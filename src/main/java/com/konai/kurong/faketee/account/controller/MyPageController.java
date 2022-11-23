package com.konai.kurong.faketee.account.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/account")
@RequiredArgsConstructor
@Controller
public class MyPageController {

    @GetMapping("/mypage")
    public String mypage(){

        return "account/mypage";
    }

    @GetMapping("/mypage/set-info")
    public String setInfo(){

        return "account/set-info";
    }
}
