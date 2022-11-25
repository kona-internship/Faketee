package com.konai.kurong.faketee.account.entity;

import com.konai.kurong.faketee.util.jpa_auditing.BaseUserEntity;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@SequenceGenerator(
        name = "EMAIL_AUTH_SEQUENCE_GENERATOR",
        sequenceName = "EMAIL_AUTH_SEQUENCE",
        initialValue = 1,
        allocationSize = 1)
@Table(name="EMAIL_AUTH")
@Data
@Entity
public class EmailAuth extends BaseUserEntity {

    /**
     * 만료시간 생성자에서 시작된 시간 이후 + max_expire_time(분)
     */
    private static final Long MAX_EXPIRE_TIME = 5L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "EMAIL_AUTH_SEQUENCE_GENERATOR")
    @Column(name = "ID")
    private Long id;

    @Column(name = "EMAIL_AUTH_TOKEN")
    private String emailAuthToken;

    private String expired;

    @Column(name = "EXPIRED_DATE")
    private LocalDateTime expiredDate;

    @OneToOne
    @JoinColumn(name = "USR_ID")
    private User user;

    @Builder
    public EmailAuth(String emailAuthToken, String expired, User user) {
        this.emailAuthToken = emailAuthToken;
        this.expired = expired;
        this.user = user;
        this.expiredDate = LocalDateTime.now().plusMinutes(MAX_EXPIRE_TIME);
    }

    public void updateExpired() {
        this.expired = "T";
    }
}
