package com.konai.kurong.faketee.account.service;

import com.konai.kurong.faketee.account.entity.EmailAuth;
import com.konai.kurong.faketee.account.repository.EmailAuthRepository;
import com.konai.kurong.faketee.util.exception.NoEmailAuthFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@EnableAsync
@Service
public class EmailAuthService {
    @Autowired
    private final JavaMailSender javaMailSender;

    private final EmailAuthRepository emailAuthRepository;

    private static final Long MAX_EXPIRE_TIME = 5L;

//    private final EmailAuthRepository emailAuthRepository;
//    private final EmailAuthRepositoryImpl emailAuthRepositoryImpl;
//
//    /**
//     * authEmail Entity 먼저 저장한다
//     * @param user
//     * @return emailAuthId
//     */
//    @Transactional
//    public Long saveAuthEmail(User user) {
//        String emailAuthToken = getEmailAuthToken(6);
//        EmailAuth emailAuth = new EmailAuth(emailAuthToken, "F");
//        return emailAuthRepository.save(emailAuth).getId();
//    }
//
//    /**
//     * id로 emailAuth READ
//     * @param id
//     * @return EmailAuth
//     */
//    public EmailAuth findById(Long id) {
//        return emailAuthRepository.findById(id).orElseThrow(() -> new NoEmailAuthFoundException());
//    }
//
//    /**
//     * 사용자 id로 emailAuth READ
//     * @param userId
//     * @return EmailAuth
//     */
//    public EmailAuth findByUserEmail(long userId) {
//        return emailAuthRepository.findByUserId(userId).orElseThrow(() -> new NoEmailAuthFoundException());
//    }
//
//    /**
//     * expired가 "F"인 만료되지 않은 emailAuth READ
//     * @param emailAuth
//     * @return
//     */
//    public EmailAuth findValidEmailAuth(EmailAuth emailAuth) {
//        return emailAuthRepositoryImpl.findValidAuthByEmail(emailAuth.getUser().getEmail(), emailAuth.getEmailAuthToken(), LocalDateTime.now())
//                .orElseThrow(NoEmailAuthFoundException::new);
//    }
//
//    /**
//     * 이메일 인증 링크 전송
//     * append url 수정 필요함
//     * @param email
//     * @return
//     */
//    @Async
//    public void sendAuthEmail(String email, Long emailAuthId) {
//        EmailAuth emailAuth = findById(emailAuthId);
//
//        //인증메일 보내기
//        try {
//            EmailUtil sendMail = new EmailUtil(mailSender);
//            sendMail.setSubject("회원가입 이메일 인증");
//            sendMail.setText(new StringBuffer().append("<h1>[이메일 인증]</h1>")
//                    .append("<p>아래 링크를 클릭하시면 이메일 인증이 완료됩니다.</p>")
//                    .append("<a href='http://localhost:8080/account/")
//                    .append(email)
//                    .append("&authKey=")
//                    .append(emailAuth.getEmailAuthToken())
//                    .append("' target='_blenk'>이메일 인증 확인</a>")
//                    .toString());
//            sendMail.setFrom("이메일 주소", "관리자");
//            sendMail.setTo(email);
//            sendMail.send();
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    /**
//     * size 크기에 맞는 난수를 생성해 EmailAuthKey 에 할당한다
//     * @param size : 난수 크기
//     * @return : EmailAuthKey
//     */
//    public String getEmailAuthToken(int size) {
//        Random random = new Random();
//        StringBuffer buffer = new StringBuffer();
//        int num = 0;
//
//        while(buffer.length() < size) {
//            num = random.nextInt(10);
//            buffer.append(num);
//        }
//        return buffer.toString();
//    }

    /**
     * email 인증 링크 전송
     * smmSetText 수정필요함
     * @param email : 받는 email
     * @param emailAuthToken
     */
    public void sendEmail(String email, String emailAuthToken) {
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setTo(email);
        smm.setSubject("시프티 회원가입 이메일 인증");
        smm.setText("http://localhost:8080/api/account/confirm-email?email=" + email + "&emailAuthToken=" + emailAuthToken);

        javaMailSender.send(smm);
    }

    /**
     * emailAuthToken CREATE
     * @param email : 받는 email
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

    public EmailAuth findByEmail(String email) {
        return emailAuthRepository.findByEmail(email).orElseThrow(() -> new NoEmailAuthFoundException());
    }
}
