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

    private String email;

    @Column(name = "EMAIL_AUTH_TOKEN")
    private String emailAuthToken;

    /**
     * "T" 라면 만료된 것이므로 email 사용 불가
     * "F" 사용 가능
     */
    private String expired;

    @Column(name = "EXPIRED_DATE")
    private LocalDateTime expiredDate;

    @Builder
    public EmailAuth(String email, String emailAuthToken, String expired) {
        this.emailAuthToken = emailAuthToken;
        this.expired = expired;
        this.expiredDate = LocalDateTime.now().plusMinutes(MAX_EXPIRE_TIME);
    }

    public void updateExpired() {
        this.expired = "T";
    }
}
