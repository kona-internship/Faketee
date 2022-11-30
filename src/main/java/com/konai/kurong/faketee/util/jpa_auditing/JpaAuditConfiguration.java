package com.konai.kurong.faketee.util.jpa_auditing;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Configuration
public class JpaAuditConfiguration {

    private final HttpSession httpSession;
    @Bean
    public AuditorAware<Long> auditorProvider(){

        return new AuditorAwareImpl(httpSession);
    }
}
