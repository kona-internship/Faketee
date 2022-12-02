package com.konai.kurong.faketee.employee.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.konai.kurong.faketee.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmpAuthInterceptor implements HandlerInterceptor {

    private final EmpAuthValidator empAuthValidator;
    private final ObjectMapper objectMapper;

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

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>session user: "+request.getSession().getAttribute("user"));
        if(!(request.getSession().getAttribute("user") instanceof SessionUser)){
            throw new Exception();
        }

        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute("user");

//        ServletInputStream inputStream = request.getInputStream();
//        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
//        DepIdsDto depIdsDto = objectMapper.readValue(messageBody, DepIdsDto.class);
//        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>session user: "+depIdsDto.getDepIds());

        return true;
    }
}
