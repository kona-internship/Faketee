package com.konai.kurong.faketee.account.repository;

import com.konai.kurong.faketee.account.entity.EmailAuth;
import com.konai.kurong.faketee.account.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailAuthRepository extends JpaRepository<EmailAuth, Long> {
    Optional<EmailAuth> findByUserUserId(Long userId);
}
