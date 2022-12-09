package com.konai.kurong.faketee.employee.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.konai.kurong.faketee.employee.utils.EmpAuthInterceptor.AUTH_EMP_KEY;
import static com.konai.kurong.faketee.employee.utils.EmpAuthInterceptor.AUTH_LOW_DEP_KEY;

/**
 * EmpAuth 어노테이션(@EmpAuth)을 사용한 메소드에 대해 권한을 확인하는 AOP 클래스이다.
 *
 * 조직관리자일 경우 자신의 조직에 대한 요청인지에 대한 여부 확인 AOP 클래스이다.
 * DepIdsDto 인터페이스를 구현한 클래스 타입의 파라미터가 있는지 확인하고
 *
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class EmpAuthAop {

    private final EmpAuthValidator empAuthValidator;

    @Pointcut("execution(* com.konai.kurong.faketee..*.*(..,com.konai.kurong.faketee.employee.utils.DepIdsDto+,..))") //com.konai.kurong.faketee.department.dto.DepartmentSaveRequestDto
    private void cut() {}

    @Before("cut() && @annotation(empAuth)")
    public void before(JoinPoint joinPoint, EmpAuth empAuth){
        log.info("==================aop=====================");
        // 세션의 유저 정보를 불러온다
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        // 조직관리자일 경우 자신의 조직에 대한 요청인지에 대한 여부 확인
        List<Object> args = List.of(joinPoint.getArgs());
        // 인터셉터에서 attribute에 넣은 값이 있는지 확인
        if (request.getAttribute(AUTH_LOW_DEP_KEY) != null) {
            for (Object arg : args) {
                if (arg instanceof DepIdsDto) {
                    ReqEmpInfo reqEmpInfo = (ReqEmpInfo) request.getAttribute(AUTH_EMP_KEY);
                    empAuthValidator.validateDepartment(reqEmpInfo.getId(), ((DepIdsDto) arg).getDepIds());
                }
            }
        }

        log.info("==================end=========================");
    }
}
