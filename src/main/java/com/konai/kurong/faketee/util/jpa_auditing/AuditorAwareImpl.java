package com.konai.kurong.faketee.util.jpa_auditing;

import com.konai.kurong.faketee.account.dto.AuthUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * Base Entity에서 생성자, 수정자를 찾아주는 클래스
 * Spring Context에서 들고있는 로그인된 유저의 pk id 값을 @CreatedBy, @LastModifiedBy 에 넣어준다
 * JpaAuditConfiguration에서 Bean으로 등록되어 사용된다
 */
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
