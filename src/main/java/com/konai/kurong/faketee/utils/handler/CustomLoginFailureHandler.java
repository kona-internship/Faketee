package com.konai.kurong.faketee.utils.handler;

import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String errorMessage;

        if(exception instanceof BadCredentialsException){
            errorMessage = "아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해주세요.";
        }else if (exception instanceof InternalAuthenticationServiceException) {
            errorMessage = "내부적으로 발생한 시스템 문제로 인해 요청을 처리할 수 없습니다. 관리자에게 문의하세요.";
        }else if (exception instanceof UsernameNotFoundException){
            errorMessage = "계정이 존재하지 않습니다. 회원가입 진행 후 로그인 해주세요.";
        }else if (exception instanceof AuthenticationCredentialsNotFoundException){
            errorMessage = "인증 요청이 거부되었습니다. 관리자에게 문의해주세요.";
        }else if (exception instanceof LockedException){
            errorMessage = "잠긴 계정입니다.";
        }else if (exception instanceof DisabledException){
            errorMessage = "비활성화된 계정입니다.";
        }else if (exception instanceof AccountExpiredException){
            errorMessage = "만료된 계정입니다.";
        }else if (exception instanceof CredentialsExpiredException){
            errorMessage = "비밀번호가 만료되었습니다.";
        }
        else {
            errorMessage = exception.getMessage();
        }
        errorMessage = URLEncoder.encode(errorMessage, "UTF-8");
        setDefaultFailureUrl("/account/login-form?error=true&exception=" + errorMessage);

        super.onAuthenticationFailure(request, response, exception);
    }
}
