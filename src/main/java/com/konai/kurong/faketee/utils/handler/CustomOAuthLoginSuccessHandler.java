package com.konai.kurong.faketee.utils.handler;

import com.konai.kurong.faketee.account.entity.User;
import com.konai.kurong.faketee.account.repository.UserRepository;
import com.konai.kurong.faketee.auth.dto.SessionUser;
import com.konai.kurong.faketee.employee.service.EmployeeService;
import com.konai.kurong.faketee.utils.exception.custom.auth.NoUserFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.konai.kurong.faketee.utils.Uri.LOGIN_REDIRECT_URI;

/**
 * OAuth 로그인 성공시 로그인한 유저 정보를 세션에 저장하는 기능을 수행한다
 *
 * OAuth 로부터 인증받은 정보 (Authentication)에서 email 을 이용해 repository 에서 user entity 를 찾고
 * httpsession 에 SessionUser 로 저장한다
 */
@RequiredArgsConstructor
@Component
public class CustomOAuthLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final EmployeeService employeeService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
        User loginUser = userRepository.findByEmail(oAuth2User.getAttributes().get("email").toString()).orElseThrow(NoUserFoundException::new);

        SessionUser sessionUser = new SessionUser(loginUser);
        sessionUser.setEmployeeIdList(employeeService.findByUserId(loginUser.getId()));
        request.getSession().setAttribute("user", sessionUser);

        response.sendRedirect(LOGIN_REDIRECT_URI);
    }
}
