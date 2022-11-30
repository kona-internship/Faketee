package com.konai.kurong.faketee.utils.handler;

import com.konai.kurong.faketee.account.entity.User;
import com.konai.kurong.faketee.account.repository.UserRepository;
import com.konai.kurong.faketee.auth.dto.SessionUser;
import com.konai.kurong.faketee.utils.exception.custom.auth.NoUserFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        User loginUser = userRepository.findByEmail(authentication.getPrincipal().toString()).orElseThrow(() -> new NoUserFoundException());

        /**
         * 로그인 성공시, authentication.getPrincipal() (로그인한 email)로 user정보를 DB에서 find해와서
         * "user" attribute에 user 정보를 저장한다.
         * SessionUser를 이용하여 user 정보를 직렬화해서 저장
         */
        request.getSession().setAttribute("user", new SessionUser(loginUser));

        /**
         * 이메일 인증이 진행되지 않은 계정에 대해 block처리
         */
        if (loginUser.getEmailAuthStatus().equals("F")){

            response.sendRedirect("/account/login-auth");
            return ;
        }

        response.sendRedirect("/account/set-auth");
    }
}
