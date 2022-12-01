package com.konai.kurong.faketee.account.controller;

import com.konai.kurong.faketee.account.dto.EmailAuthRequestDto;
import com.konai.kurong.faketee.account.dto.UserJoinRequestDto;
import com.konai.kurong.faketee.account.service.EmailAuthService;
import com.konai.kurong.faketee.account.service.UserService;
import com.konai.kurong.faketee.auth.LoginUser;
import com.konai.kurong.faketee.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@RequestMapping("/api/account")
@RequiredArgsConstructor
@RestController
@Slf4j
public class RegisterApiController {

    private final UserService userService;
    private final EmailAuthService emailAuthService;
    private final HttpServletRequest httpServletRequest;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserJoinRequestDto userJoinRequestDto){

        return ResponseEntity.ok(userService.join(userJoinRequestDto));
    }

    @GetMapping("/check-email")
    public ResponseEntity<?> checkEmail(@RequestParam("email") String email){

        return ResponseEntity.ok(userService.validateEmail(email));
    }

    @GetMapping("/confirm-email")
    public ResponseEntity<?> confirmEmail(@ModelAttribute EmailAuthRequestDto emailAuthRequestDto) {

        userService.confirmEmailAuth(emailAuthRequestDto);
        /**
         * session 초기화
         */
        httpServletRequest.getSession().invalidate();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/account/auth-complete"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @GetMapping("/reSend-email")
    public ResponseEntity<?> reSendEmail() {
        SessionUser sessionUser = (SessionUser) httpServletRequest.getSession().getAttribute("user");

        String email = sessionUser.getEmail();
        String emailAuthToken = emailAuthService.updateEmailAuthToken(email);
        emailAuthService.sendEmail(email, emailAuthToken);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/account/auth-complete"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

}
