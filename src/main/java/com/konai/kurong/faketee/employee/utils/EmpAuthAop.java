package com.konai.kurong.faketee.employee.utils;

import com.konai.kurong.faketee.auth.dto.SessionUser;
import com.konai.kurong.faketee.employee.entity.Employee;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
@RequiredArgsConstructor
public class EmpAuthAop {

    private final EmpAuthValidator empAuthValidator;

//    @Pointcut("execution(* com.konai.kurong.faketee..*.*(..))")
//    private void cut() {}

    @Before("execution(* com.konai.kurong.faketee..*.*(..)) && @annotation(empAuth)")
    public void before(JoinPoint joinPoint, EmpAuth empAuth){

        // 세션의 유저 정보를 불러온다
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute("user");

        Long corId = null;
        List<String> urlPathList = Arrays.asList(request.getRequestURI().split("/"));
        int index = urlPathList.indexOf("corporation");
        if(urlPathList.size()>index+1) {
            corId = Long.parseLong(urlPathList.get(index + 1));
        }

        if(corId == null){
            throw new RuntimeException();
        }
        Employee employee =empAuthValidator.validateCorporation(corId, sessionUser.getId());
        empAuthValidator.validateEmployee(empAuth.role(), employee.getId());

        List<Object> args = List.of(joinPoint.getArgs());
        for(Object arg: args){
            if(arg instanceof DepIdsDto){
                // 요청한 사람이 조직 관리자일 경우
                if(employee.getRole().equals(EmpRole.GROUP_MANAGER) && empAuth.onlyLowDep()){
                    empAuthValidator.validateDepartment(corId, employee.getCorporation().getId(), ((DepIdsDto) arg).getDepIds());
                }
            }
        }
    }
}
