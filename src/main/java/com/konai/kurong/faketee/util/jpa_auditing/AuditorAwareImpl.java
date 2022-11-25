package com.konai.kurong.faketee.util.jpa_auditing;

import com.konai.kurong.faketee.account.dto.AuthUserDto;
import com.konai.kurong.faketee.config.auth.SessionUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * Base Entity에 필요한 생성자, 수정자의 ID를 찾아주는 클래스
 * Spring Context에서 들고있는 로그인된 유저의 pk id 값을 @CreatedBy, @LastModifiedBy 에 넣어준다
 * JpaAuditConfiguration에서 Bean으로 등록되어 사용된다
 */
@RequiredArgsConstructor
@Slf4j
public class AuditorAwareImpl implements AuditorAware<Long> {

    private final HttpSession httpSession;
//    @Override
//    public Optional<Long> getCurrentAuditor() {
//        Object authenticationPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        /**
//         * 회원가입 시에는 anonymousUser 이므로 null 반환
//         */
//        if(authenticationPrincipal.equals("anonymousUser")) {
//            log.debug("Not found Authentication Principal");
//            return null;
//        }
//
//        AuthUserDto userDto = (AuthUserDto)authenticationPrincipal;
//        log.debug("Found Principal Details: {}", userDto);
//        return Optional.of(userDto.getId());
//    }

    @Override
    public Optional<Long> getCurrentAuditor() {

        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if(user == null)
            return null;

        return Optional.ofNullable(user.getId());
    }
}
