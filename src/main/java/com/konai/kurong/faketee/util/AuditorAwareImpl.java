package com.konai.kurong.faketee.util;

import com.konai.kurong.faketee.account.dto.AuthUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Slf4j
public class AuditorAwareImpl implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {

        AuthenticationPrincipal authenticationPrincipal = (AuthenticationPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(authenticationPrincipal == null) {
            log.debug("Not found Authentication Principal");
            return null;
        }

        AuthUserDto userDto = (AuthUserDto)authenticationPrincipal;
        log.debug("Found Principal Details: {}", userDto);
        return Optional.of(userDto.getId());
    }
}
