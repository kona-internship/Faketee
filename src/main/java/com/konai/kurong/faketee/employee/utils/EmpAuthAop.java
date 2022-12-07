package com.konai.kurong.faketee.employee.utils;

import com.konai.kurong.faketee.auth.dto.SessionUser;
import com.konai.kurong.faketee.employee.entity.Employee;
import com.konai.kurong.faketee.utils.parse.UrlParser;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * EmpAuth 어노테이션(@EmpAuth)을 사용한 메소드에 대해 권한을 확인하는 AOP 클래스이다.
 *
 * 검증 가능한 목록:
 * 1. 요청을 보낸 사용자가 요청된 회사의 직원인지 여부
 * 2. 직원이 보낸 요청에 대한 권한이 있는지 여부 (어노테이션을 통해 확인)
 * 3. 조직관리자일 경우 자신의 조직에 대한 요청인지에 대한 여부
 *
 */
@Aspect
@Component
@RequiredArgsConstructor
public class EmpAuthAop {

    private final EmpAuthValidator empAuthValidator;
    private final UrlParser urlParser;

//    @Pointcut("execution(* com.konai.kurong.faketee..*.*(..))")
//    private void cut() {}

    @Before("execution(* com.konai.kurong.faketee..*.*(..)) && @annotation(empAuth)")
    public void before(JoinPoint joinPoint, EmpAuth empAuth){

        // 세션의 유저 정보를 불러온다
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute("user");

        // 요청된 url에서 회사 id 파싱
        Long corId = urlParser.parseCor(request);
        if(corId == null){
            throw new RuntimeException();
        }

        // 요청을 보낸 사용자가 요청된 회사의 직원인지 여부 확인
        Employee employee = empAuthValidator.validateCorporation(corId, sessionUser.getId());

        // 직원이 보낸 요청에 대한 권한이 있는지 여부 확인
        empAuthValidator.validateEmployee(empAuth.role(), employee.getId());

        // 조직관리자일 경우 자신의 조직에 대한 요청인지에 대한 여부 확인
        List<Object> args = List.of(joinPoint.getArgs());
        for(Object arg: args){
            if(arg instanceof DepIdsDto){
                // 요청한 사람이 조직 관리자일 경우 + 메서드의 권한이 조직관리사 이상의 권한 요구할 시 + 하위 조직인지 여부를 확인해야 할 시 (어노테이션에 속성으로 체크)
                if(employee.getRole().equals(EmpRole.GROUP_MANAGER) && empAuth.role().compareTo(EmpRole.GROUP_MANAGER)<=0 && empAuth.onlyLowDep()){
                    empAuthValidator.validateDepartment(corId, employee.getCorporation().getId(), ((DepIdsDto) arg).getDepIds());
                }
            }
        }
    }
}
