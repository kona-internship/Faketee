package com.konai.kurong.faketee.account.controller;

import com.konai.kurong.faketee.auth.PrincipalDetails;
import com.konai.kurong.faketee.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@RequestMapping("/account")
@RequiredArgsConstructor
@Controller
public class RegisterController {

    private final HttpSession httpSession;

    @GetMapping("/")
    public String home(){

        return "account/home";
    }

    @GetMapping("/login-form")
    public String loginForm(@RequestParam(value = "error", required = false)String error,
                            @RequestParam(value = "exception", required = false)String exception,
                            Model model){

        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "account/login-form";
    }

    @GetMapping("/register-form")
    public String registerForm(){

        return "account/register-form";
    }

    @GetMapping("/set-auth")
    public String setAuth(@AuthenticationPrincipal PrincipalDetails principalDetails){

        return "account/set-auth";
    }

    @GetMapping("/login-error")
    public String loginError(){

        return "account/login-error";
    }
}
