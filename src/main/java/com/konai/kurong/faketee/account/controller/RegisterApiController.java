package com.konai.kurong.faketee.account.controller;

import com.konai.kurong.faketee.account.dto.UserJoinRequestDto;
import com.konai.kurong.faketee.account.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/register")
@RequiredArgsConstructor
@RestController
public class RegisterApiController {

    private final UserService userService;

    @PostMapping("/")
    public ResponseEntity<?> register(@RequestBody UserJoinRequestDto userJoinRequestDto){

        return ResponseEntity.ok(userService.join(userJoinRequestDto));
    }

    @GetMapping("/check-email")
    public ResponseEntity<?> checkEmail(@RequestParam("email") String email){

        return ResponseEntity.ok(userService.validateEmail(email));
    }
}
