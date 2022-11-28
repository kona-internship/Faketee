package com.konai.kurong.faketee.account.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/account")
@Controller
public class RegisterController {

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
    public String setAuth(){

        return "account/set-auth";
    }
}
