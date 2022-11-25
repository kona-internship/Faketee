package com.konai.kurong.faketee.config.auth;


import com.konai.kurong.faketee.account.dto.UserJoinRequestDto;
import com.konai.kurong.faketee.account.entity.User;
import com.konai.kurong.faketee.account.repository.UserRepository;
import com.konai.kurong.faketee.account.util.Role;
import com.konai.kurong.faketee.account.util.Type;
import com.konai.kurong.faketee.config.auth.dto.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;


/**
 * CustomOAuth2UserService
 * 소셜 로그인 이후 가져온 사용자의 정보 (email, name 등)들을 기반으로 가입 및 정보수정, 세션저장 등의 기능 지원
 */
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        /**
         * registrationID: 현재 로그인 진행중인 서비스를 구분하는 코드
         * userNameAttributeName: OAuth2 로그인 진행 시 키가 되는 필드값(Primary Key와 같은 의미)
         *                        구글의 경우 기본 코드 지원 'sub', 네이버 카카오 등은 지원 X
         * OAuthAttributes: OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스
         * SessionUser: 세션에 사용자 정보를 저장하기 위한 클래스
         */
        String registrationID = userRequest
                .getClientRegistration()
                .getRegistrationId();
        String userNameAttributeName = userRequest
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.
                of(registrationID, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);
//        User user = UserJoinRequestDto.builder()
//                .email(attributes.getEmail())
//                .name(attributes.getName())
//                .password("temp password")
//                .role(Role.USER)
//                .type(Type.GOOGLE)
//                .build()
//                .toEntity();
        httpSession.setAttribute("user", new SessionUser(user));
//        userRepository.save(user);

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRole().getKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes){

        User user = userRepository.findByEmail(attributes.getEmail())
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
