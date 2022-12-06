package com.konai.kurong.faketee.account.service;

import com.konai.kurong.faketee.account.entity.EmailAuth;
import com.konai.kurong.faketee.account.repository.EmailAuthRepository;
import com.konai.kurong.faketee.account.repository.EmailAuthRepositoryImpl;
import com.konai.kurong.faketee.utils.exception.custom.auth.NoEmailAuthFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@RequiredArgsConstructor
@EnableAsync
@Service
public class EmailAuthService {
    @Autowired
    private final JavaMailSender javaMailSender;
    private final EmailAuthRepository emailAuthRepository;
    private final EmailAuthRepositoryImpl emailAuthRepositoryImpl;
    private static final Long MAX_EXPIRE_TIME = 5L;

    /**
     * email 인증 링크 전송
     * smmSetText emailAuthToken 이 노출된다
     * @param email : 받는 email
     * @param emailAuthToken : 생성된 emailAuthToken
     */
    public void sendEmail(String email, String emailAuthToken) {

        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setFrom("konai.faketee@gmail.com");
        smm.setTo(email);
        smm.setSubject("FAKETEE 회원가입 이메일 인증");
        smm.setText("http://localhost:8080/api/account/confirm-email?email=" + email + "&emailAuthToken=" + emailAuthToken);

        javaMailSender.send(smm);
    }

    /**
     * emailAuthToken CREATE
     * @param email : 받는 email
     * @return emailAuthToken
     */
    public String saveEmailAuthToken(String email) {

        LocalDateTime expireDate = LocalDateTime.now().plusMinutes(MAX_EXPIRE_TIME);

        EmailAuth emailAuth = emailAuthRepository.save(
                EmailAuth.builder()
                        .email(email)
                        .emailAuthToken(UUID.randomUUID().toString())
                        .expired("F")
                        .expireDate(expireDate)
                        .build());

        return emailAuth.getEmailAuthToken();
    }

    /**
     * email 재전송 시, EmailAuthToken UPDATE
     * @param email
     * @return emailAuthToken
     */
    @Transactional
    public String updateEmailAuthToken(String email) {

        LocalDateTime expireDate = LocalDateTime.now().plusMinutes(MAX_EXPIRE_TIME);
        emailAuthRepositoryImpl.updateEmailAuth(email, UUID.randomUUID().toString(), expireDate);
        return findByEmail(email).getEmailAuthToken();
    }

    /**
     * email 로 EmailAuth FIND
     * @param email : 이메일
     * @return emailAuth
     */
    public EmailAuth findByEmail(String email) {

        return emailAuthRepository.findByEmail(email).orElseThrow(() -> new NoEmailAuthFoundException());
    }

    /**
     * 직원 등록 시, 인증코드 생성하기
     * @return joinCode
     */
    public String createJoinCode() {
        StringBuffer joinCode = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 6; i++) {
            int index = rnd.nextInt(3);
            switch (index) {
                case 0:
                    joinCode.append(((int) (rnd.nextInt(26)) + 97));
                    break;
                case 1:
                    joinCode.append(((int) (rnd.nextInt(26)) + 65));
                    break;
                case 2:
                    joinCode.append((rnd.nextInt(10)));
                    break;
            }
        }
        return joinCode.toString();
    }

    /**
     * 회사 합류 코드 직원에게 전송하기
     * @param email : 직원의 email
     * @param joinCode : 회사 합류 코드
     */
    public void sendJoinCode(String email, String joinCode) {
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setFrom("konai.faketee@gmail.com");
        smm.setTo(email);
        smm.setSubject("FAKETEE 회사 합류코드 인증");
        smm.setText("회사 합류코드 : < " + joinCode + " >를 입력해주세요");

        javaMailSender.send(smm);
    }
}