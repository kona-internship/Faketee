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


@RequiredArgsConstructor
@Component
@Slf4j
public class EmployeeHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("==================resolver=====================\nhas: "+ parameter.hasParameterAnnotation(ReqEmp.class));
        log.info("type: "+ ReqEmpInfo.class.isAssignableFrom(parameter.getParameterType()));
        log.info("==================end=========================");
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
