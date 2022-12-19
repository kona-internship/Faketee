package com.konai.kurong.faketee.utils.handler;

import com.konai.kurong.faketee.account.entity.User;
import com.konai.kurong.faketee.account.repository.UserRepository;
import com.konai.kurong.faketee.auth.dto.SessionUser;
import com.konai.kurong.faketee.employee.service.EmployeeService;
import com.konai.kurong.faketee.utils.exception.custom.auth.NoUserFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.konai.kurong.faketee.utils.Uri.LOGIN_REDIRECT_URI;
import static com.konai.kurong.faketee.utils.Uri.USER_UNAUTHORIZED_URI;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final EmployeeService employeeService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        User loginUser = userRepository.findByEmail(authentication.getPrincipal().toString()).orElseThrow(NoUserFoundException::new);

        /**
         * 이메일 인증이 진행되지 않은 계정에 대해 block 처리
         */
        if (loginUser.getEmailAuthStatus().equals("F")) {

            response.sendRedirect(USER_UNAUTHORIZED_URI);
            return;
        }

        /**
         * 로그인 성공시, authentication.getPrincipal() (로그인한 email)로 user 정보를 DB 에서 find 해와서
         * "user" attribute 에 user 정보를 저장한다.
         * SessionUser 를 이용하여 user 정보를 직렬화해서 저장
         */
        SessionUser sessionUser = new SessionUser(loginUser);
        sessionUser.setEmployeeIdList(employeeService.convertToSessionDto(employeeService.findByUserId(loginUser.getId())));
        request.getSession().setAttribute("user", sessionUser);

        response.sendRedirect(LOGIN_REDIRECT_URI);
    }
}
