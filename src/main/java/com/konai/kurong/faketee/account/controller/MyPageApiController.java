package com.konai.kurong.faketee.account.controller;

import com.konai.kurong.faketee.account.dto.UserUpdateRequestDto;
import com.konai.kurong.faketee.account.service.UserService;
import com.konai.kurong.faketee.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/account")
@RequiredArgsConstructor
@RestController
public class MyPageApiController {

    private final UserService userService;

    @PostMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody UserUpdateRequestDto requestDto,
                                            @AuthenticationPrincipal PrincipalDetails principalDetails){

        return ResponseEntity.ok(userService.updatePassword(principalDetails.getId(), requestDto));
    }

    @PostMapping("/delete")
    public void delete(@AuthenticationPrincipal PrincipalDetails principalDetails){

        userService.delete(principalDetails.getId());
    }
}
