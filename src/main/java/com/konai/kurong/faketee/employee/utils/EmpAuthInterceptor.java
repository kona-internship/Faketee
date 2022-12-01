package com.konai.kurong.faketee.employee.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class EmpAuthInterceptor implements HandlerInterceptor {

    private final EmpAuthValidator empAuthValidator;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        EmpAuth empAuth = handlerMethod.getMethodAnnotation(EmpAuth.class);
        if(empAuth == null){
            return true;
        }

        return true;
    }
}
