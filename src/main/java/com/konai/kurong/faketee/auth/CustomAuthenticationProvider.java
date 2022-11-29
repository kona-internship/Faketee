package com.konai.kurong.faketee.auth;

import com.konai.kurong.faketee.auth.PrincipalDetails;
import com.konai.kurong.faketee.auth.PrincipalDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 로그인 처리를 위해 authentication provider가 필요함
 * username password authentication token 발급을 위한 커스텀 클래스
 * SecurityConfig에서 등록된다
 */
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final PrincipalDetailsService principalDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        PrincipalDetails principalDetails = (PrincipalDetails) principalDetailsService.loadUserByUsername(username);

        if(!bCryptPasswordEncoder.matches(password, principalDetails.getPassword())){

            throw new BadCredentialsException(username);
        }
        if(!principalDetails.isEnabled()){

            throw new BadCredentialsException(username);
        }

        /**
         * Spring 기본인 AbstractUserDetailAuthenticationProvider.class와 다르게 UsernamePasswordAuthenticationToken을 커스텀하여 저장
         * @AuthenticationPrincipal PrincipalDetails 사용이 불가하다
         * Authentication Token의 principal로 username을 주었으니, (@AuthenticationPrincipal String email) 사용하면 로그인한 유저의 email이 불러와진다.
         */
        return new UsernamePasswordAuthenticationToken(username, password, principalDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {

        return true;
    }

    public void setbCryptPasswordEncoder(BCryptPasswordEncoder encoder) {

        this.bCryptPasswordEncoder = encoder;
    }
}
