package com.konai.kurong.faketee.account.controller;

import com.konai.kurong.faketee.account.dto.UserUpdateRequestDto;
import com.konai.kurong.faketee.account.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/account")
@RequiredArgsConstructor
@RestController
public class MyPageApiController {

    private final UserService userService;

    @PostMapping("/update-password/{userID}")
    public ResponseEntity<?> updatePassword(@PathVariable Long userID, @RequestBody UserUpdateRequestDto requestDto){

        return ResponseEntity.ok(userService.updatePassword(userID, requestDto));
    }

    @PostMapping("/delete/{userID}")
    public void delete(@PathVariable Long userID){

        userService.delete(userID);
    }
}
