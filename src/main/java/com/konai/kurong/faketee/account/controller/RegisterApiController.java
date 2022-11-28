package com.konai.kurong.faketee.account.controller;

import com.konai.kurong.faketee.account.dto.EmailAuthRequestDto;
import com.konai.kurong.faketee.account.dto.UserJoinRequestDto;
import com.konai.kurong.faketee.account.service.EmailAuthService;
import com.konai.kurong.faketee.account.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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

    /**
     * 반환 타입 고려 필요함
     * void 형으로 할지, 아니면 다른 것과 같이 responseEntity 로 묶을지!!!!!!!!!
     * @param email
     */
    @GetMapping("/send-email")
    public void sendEmail(@RequestParam("email") String email){
        String emailAuthToken = emailAuthService.saveEmailAuthToken(email);
        emailAuthService.sendEmail(email, emailAuthToken);
    }

    @GetMapping("/confirm-email")
    public ResponseEntity<?> confirmEmail(@ModelAttribute EmailAuthRequestDto emailAuthRequestDto) {
        return ResponseEntity.ok(userService.confirmEmailAuth(emailAuthRequestDto));
    }
}
