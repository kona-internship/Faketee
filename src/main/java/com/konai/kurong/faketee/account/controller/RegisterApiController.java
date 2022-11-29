package com.konai.kurong.faketee.account.controller;

import com.konai.kurong.faketee.account.dto.EmailAuthRequestDto;
import com.konai.kurong.faketee.account.dto.UserJoinRequestDto;
import com.konai.kurong.faketee.account.service.EmailAuthService;
import com.konai.kurong.faketee.account.service.UserService;
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
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/account/login-form"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @GetMapping("/reconfirm-email")
    public ResponseEntity<?> reconfirmEmail(HttpServletRequest httpServletRequest) {
        String email = (String) httpServletRequest.getSession().getAttribute("guest");
        String emailAuthToken = emailAuthService.updateEmailAuthToken(email);
        EmailAuthRequestDto emailAuthRequestDto = new EmailAuthRequestDto(email, emailAuthToken);
        emailAuthService.sendEmail(email, emailAuthToken);
//        userService.confirmEmailAuth(emailAuthRequestDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/account/login-form"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }
}
