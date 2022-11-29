package com.konai.kurong.faketee.account.controller;

import com.konai.kurong.faketee.account.dto.UserUpdateRequestDto;
import com.konai.kurong.faketee.account.service.UserService;
import com.konai.kurong.faketee.auth.PrincipalDetails;
import com.konai.kurong.faketee.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/api/account")
@RequiredArgsConstructor
@RestController
public class MyPageApiController {

    private final UserService userService;

    @PostMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody UserUpdateRequestDto requestDto,
                                            HttpServletRequest request){
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute("user");
        return ResponseEntity.ok(userService.updatePassword(sessionUser.getId(), requestDto));
    }

    @PostMapping("/delete")
    public void delete(HttpServletRequest request){
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute("user");
        userService.delete(sessionUser.getId());
    }
}
