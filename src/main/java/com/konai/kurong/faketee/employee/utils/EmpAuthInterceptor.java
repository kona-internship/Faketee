package com.konai.kurong.faketee.employee.utils;

import com.konai.kurong.faketee.auth.dto.SessionUser;
import com.konai.kurong.faketee.employee.entity.Employee;
import com.konai.kurong.faketee.utils.parse.UrlParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 직원의 권한을 검증하는 인터셉터.
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class EmpAuthInterceptor implements HandlerInterceptor {

    private final EmpAuthValidator empAuthValidator;
    private final UrlParser urlParser;

    public static final String AUTH_EMP_KEY = "emp";
    public static final String AUTH_LOW_DEP_KEY = "lowDep";

    /**
     * 요청을 보낸 사용자가 요청된 회사의 직원인지 여부 확인한다.
     * 요청하는 직원의 권한을 식별하고 타겟 메서드(요청된 url에 매핑된 컨트롤러)에 있는 권한과 비교하여 직원이 보낸 요청에 대한 권한이 있는지 여부 확인한다..
     * 직원에 대한 정보를 HttpServletRequest의 attribute에 넣어 전달한다.
     * 하위 조직인지 여부를 확인해야 할 시 HttpServletRequest의 attribute에 관련 여부를 넣어 전달한다.
     *
     * @param request current HTTP request
     * @param response current HTTP response
     * @param handler chosen handler to execute, for type and/or instance evaluation
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("==================interceptor=====================");

        // HandlerMethod를 변수로 받는다.
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        // EmpAuth 어노테이션을 불러오고 메서드에 해당 어노테이션이 없을 시 해당 메서드를 진행하도록 true를 반환한다.
        EmpAuth empAuth = handlerMethod.getMethodAnnotation(EmpAuth.class);
        if(empAuth == null){
            return true;
        }

        // 세션의 유저 정보를 불러온다
        if(!(request.getSession().getAttribute("user") instanceof SessionUser)){
            throw new Exception();
        }
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute("user");

        // 요청된 url에서 회사 id 파싱
        Long corId = urlParser.parseCor(request);
        if(corId == null){
            throw new RuntimeException();
        }

        // 요청을 보낸 사용자가 요청된 회사의 직원인지 여부 확인
        ReqEmpInfo reqEmpInfo = empAuthValidator.validateCorporation(corId, sessionUser.getId());

        // 직원이 보낸 요청에 대한 권한이 있는지 여부 확인
        empAuthValidator.validateEmployee(empAuth.role(), reqEmpInfo);

        request.setAttribute(AUTH_EMP_KEY, reqEmpInfo);

        // 요청한 사람이 조직 관리자일 경우 + 메서드의 권한이 조직관리사 이상의 권한 요구할 시 + 하위 조직인지 여부를 확인해야 할 시 (어노테이션에 속성으로 체크)
        if(reqEmpInfo.getRole().equals(EmpRole.GROUP_MANAGER) && empAuth.role().compareTo(EmpRole.GROUP_MANAGER)<=0 && empAuth.onlyLowDep()){
            request.setAttribute(AUTH_LOW_DEP_KEY, true);
        }

        log.info("==================end=========================");
        return true;
    }
}
