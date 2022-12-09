package com.konai.kurong.faketee.employee.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

import static com.konai.kurong.faketee.employee.utils.EmpAuthInterceptor.AUTH_EMP_KEY;

/**
 * ReqEmp 어노테이션을 사용하여 파라미터에 매핑하는 로직을 수행한다.
 *
 * supportsParameter(..): 어노테이션이 있는지 타입이 같은지 검증하여 resolveArgument 메소드가 수행될지 판단하는 메서드.
 * resolveArgument(..): 어노테이션이 사용된 파라미터에 매핑하는 HttpServletRequest에 있는 ReqEmpInfo 클래스의 직원 정보를 매핑한다..
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class EmployeeHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ReqEmp.class)&&ReqEmpInfo.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        log.info("==================resolver=====================");
        log.info("start");
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        log.info("request: "+ request.getAttribute(AUTH_EMP_KEY));
        ReqEmpInfo reqEmpInfo = (ReqEmpInfo) request.getAttribute(AUTH_EMP_KEY);
        log.info("empinfo: "+ reqEmpInfo.toString());
        if(reqEmpInfo==null){
            throw new IllegalArgumentException();
        }
        log.info("==================end=========================");

        return reqEmpInfo;
    }
}
