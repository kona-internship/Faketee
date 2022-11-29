package com.konai.kurong.faketee.auth;

import com.konai.kurong.faketee.account.repository.UserRepository;
import com.konai.kurong.faketee.util.CustomLoginFailureHandler;
import com.konai.kurong.faketee.util.CustomLoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class SecurityConfig {


    private final CustomOAuth2UserService customOAuth2UserService;
    private final UserRepository userRepository;
    private final CustomLoginFailureHandler customLoginFailureHandler;
    private final CustomLoginSuccessHandler customLoginSuccessHandler;
    private final PrincipalDetailsService principalDetailsService;


    @Bean
    public UserDetailsService userDetailsService() {

        return new PrincipalDetailsService(userRepository);
    }

    /** 순환참조 걸릴시 이 bean은 삭제하고 AuthenticationManager는 사용하지 않을것 **/
//    @Bean
//    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//
//        return authenticationConfiguration.getAuthenticationManager();
//    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    /**
     * Spring의 기본인 AbstractUserDetailAuthenticationProvider를 사용하지 않고 CustomAuthenticationProvider사용
     * Spring 기본인 AbstractUserDetailAuthenticationProvider를 사용하면 UserDetailService(PrincipalDetailsService)에서 반환한 객체를 Principal로 세팅하기에
     * @AuthenticationPrincipal 어노테이션으로 PrincipalDetails 접근이 가능했었다.
     * CustomAuthenticationProvider에서는 PrincipalDetails의 username과 password 정보만 꺼내서 String으로 UsernamePasswordAuthenticationToken에 넣었다.
     * @return
     */
    @Bean
    public CustomAuthenticationProvider authenticationProvider(){

        CustomAuthenticationProvider customAuthenticationProvider = new CustomAuthenticationProvider(principalDetailsService);
        customAuthenticationProvider.setbCryptPasswordEncoder(passwordEncoder());
        return customAuthenticationProvider;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        return (web -> web
                        .ignoring()
                        .antMatchers("/resources/**",
                                    "/static/**"));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http
                .authorizeRequests()
                    .antMatchers( /**추후 permit all url 수정 */
                            "/",
                            "/account/",
                            "/account/login-form",
                            "/account/register-form",
                            "/api/**"
                    )
                    .permitAll()
//                    .antMatchers("/api/**")
//                    .hasRole(Role.USER.name())
                    .antMatchers("/account/**").hasRole("USER")
                    .anyRequest()
                    .authenticated()
                /**
                 * 로그인 성공 시 redirect 페이지 결정 안되어 있음
                 * 일단은 직원 / 최고관리자 결정하는 페이지로 이동
                 */
                .and()
                    .formLogin()
//                        .usernameParameter("email")
                        .loginPage("/account/login-form")
                        .loginProcessingUrl("/login")
                        .successHandler(customLoginSuccessHandler)
                        .failureHandler(customLoginFailureHandler)
                        //.defaultSuccessUrl("/account/set-auth")
                        .permitAll()
                .and()
                    .logout()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true) // 세션 날리기
//                        .permitAll()
                .and()
                    .oauth2Login()
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService);

        return http.build();
    }
}
