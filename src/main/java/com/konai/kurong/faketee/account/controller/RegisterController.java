package com.konai.kurong.faketee.account.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/account")
@Controller
public class RegisterController {

    @GetMapping("/")
    public String home(){

        return "account/home";
    }

    @GetMapping("/login-form")
    public String loginForm(){

        return "account/login-form";
    }

    @GetMapping("/register-form")
    public String registerForm(){

        return "account/register-form";
    }
}
