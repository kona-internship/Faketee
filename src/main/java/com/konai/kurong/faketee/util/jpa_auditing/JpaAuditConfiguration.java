package com.konai.kurong.faketee.util.jpa_auditing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class JpaAuditConfiguration {

    @Bean
    public AuditorAware<Long> auditorProvider(){

        return new AuditorAwareImpl();
    }
}
