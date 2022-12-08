package com.konai.kurong.faketee.employee.utils;

import com.konai.kurong.faketee.auth.dto.SessionUser;
import com.konai.kurong.faketee.employee.entity.Employee;
import com.konai.kurong.faketee.utils.parse.UrlParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

import static com.konai.kurong.faketee.employee.utils.EmpAuthInterceptor.AUTH_EMP_KEY;
import static com.konai.kurong.faketee.employee.utils.EmpAuthInterceptor.AUTH_LOW_DEP_KEY;

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
@Order(1)
@Slf4j
public class EmpAuthAop {

    private final EmpAuthValidator empAuthValidator;
    private final UrlParser urlParser;


    @Pointcut("execution(* com.konai.kurong.faketee..*.*(..,com.konai.kurong.faketee.employee.utils.DepIdsDto+,..))") //com.konai.kurong.faketee.department.dto.DepartmentSaveRequestDto
    private void cut() {}

    @Before("cut() && @annotation(empAuth)")
    public void before(JoinPoint joinPoint, EmpAuth empAuth){
        log.info("==================aop=====================");
        // 세션의 유저 정보를 불러온다
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        log.info("request: "+ request.getAttribute(AUTH_EMP_KEY));

        // 조직관리자일 경우 자신의 조직에 대한 요청인지에 대한 여부 확인
        List<Object> args = List.of(joinPoint.getArgs());
        for(Object arg: args){
            if(arg instanceof DepIdsDto){
                // 요청한 사람이 조직 관리자일 경우 + 메서드의 권한이 조직관리사 이상의 권한 요구할 시 + 하위 조직인지 여부를 확인해야 할 시 (어노테이션에 속성으로 체크)
                if(request.getAttribute(AUTH_LOW_DEP_KEY) != null){
                    ReqEmpInfo reqEmpInfo = (ReqEmpInfo) request.getAttribute(AUTH_EMP_KEY);
                    empAuthValidator.validateDepartment(reqEmpInfo.getId(), ((DepIdsDto) arg).getDepIds());
                }
            }
        }

        log.info("==================end=========================");
    }
}
