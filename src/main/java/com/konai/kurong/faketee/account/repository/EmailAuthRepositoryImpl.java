package com.konai.kurong.faketee.account.repository;

import com.konai.kurong.faketee.account.entity.EmailAuth;
import com.konai.kurong.faketee.account.entity.QEmailAuth;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDateTime;
import java.util.Optional;

public class EmailAuthRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public EmailAuthRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(EmailAuth.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Optional<EmailAuth> findValidAuthByEmail(String email, String emailAuthToken, LocalDateTime currentTime) {
        EmailAuth emailAuth = jpaQueryFactory
                .selectFrom(QEmailAuth.emailAuth)
                .where(QEmailAuth.emailAuth.email.eq(email),
                        QEmailAuth.emailAuth.emailAuthToken.eq(emailAuthToken),
                        QEmailAuth.emailAuth.expireDate.goe(currentTime),
                        QEmailAuth.emailAuth.expired.eq("F"))
                .fetchFirst();

        return Optional.ofNullable(emailAuth);
    }

    public void updateEmailAuth(String email, String emailAuthToken, LocalDateTime expireDate) {
       jpaQueryFactory.update(QEmailAuth.emailAuth)
                .set(QEmailAuth.emailAuth.emailAuthToken, emailAuthToken)
                .set(QEmailAuth.emailAuth.expireDate, expireDate)
                .set(QEmailAuth.emailAuth.expired, "F")
                .where(QEmailAuth.emailAuth.email.eq(email))
                .execute();
    }
}
