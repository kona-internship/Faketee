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
import java.util.ArrayList;
import java.util.List;

import static com.konai.kurong.faketee.employee.utils.EmpAuthInterceptor.AUTH_EMP_KEY;

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

    private final EmpAuthParamValidator empAuthParamValidator;

    @Pointcut("execution(* com.konai.kurong.faketee..*.*(..,com.konai.kurong.faketee.employee.utils.AuthIdsDto+,..))") //com.konai.kurong.faketee.department.dto.DepartmentSaveRequestDto
    private void cut() {}

    @Before("cut() && @annotation(empAuth)")
    public void before(JoinPoint joinPoint, EmpAuth empAuth){
        log.info("==================aop=====================");
        // 세션의 유저 정보를 불러온다
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();


        List<Object> args = List.of(joinPoint.getArgs());

        if (empAuth.paramCheckType().equals(EmpAuthParamType.NO_CHECK)) {
            return;
        }
        ReqEmpInfo reqEmpInfo = (ReqEmpInfo) request.getAttribute(AUTH_EMP_KEY);

        List<Long> idList = new ArrayList<>();

        for (Object arg : args) {
            if (arg instanceof AuthIdsDto) {
                idList = ((AuthIdsDto) arg).getEmpAuthCheckList().getIdList();
                empAuthParamValidator.validateDepartment(reqEmpInfo.getId(), ((AuthIdsDto) arg).getEmpAuthCheckList().getIdList());
            }
        }

        if(idList.isEmpty()){
            return;
        }

        switch (empAuth.paramCheckType()){
            case DEP_LOW:
                empAuthParamValidator.validateDepartment(reqEmpInfo.getId(), idList);
                break;
            case DRAFT_REQ:
                empAuthParamValidator.validateDraft(reqEmpInfo.getId(), idList);
                break;
        }

        log.info("==================end=========================");
    }
}
