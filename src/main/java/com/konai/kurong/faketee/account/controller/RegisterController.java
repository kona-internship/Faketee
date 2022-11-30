package com.konai.kurong.faketee.account.controller;

import com.konai.kurong.faketee.account.util.Role;
import com.konai.kurong.faketee.auth.LoginUser;
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

    @GetMapping("/")
    public String home(){

        return "account/home";
    }

    @GetMapping("/login-form")
    public String loginForm(@RequestParam(value = "error", required = false)String error,
                            @RequestParam(value = "exception", required = false)String exception,
                            @LoginUser SessionUser sessionUser,
                            Model model){

        if(sessionUser != null && sessionUser.getRole().equals(Role.USER)){
            return "redirect:http://localhost:8080/account/set-auth";
        }
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

    @GetMapping("/login-auth")
    public String loginError(){

        return "account/login-auth";
    }

    @GetMapping("/register-auth")
    public String registerAuth(){

        return "account/register-auth";
    }

    @GetMapping("/auth-complete")
    public String authComplete(){

        return "account/auth-complete";
    }
}
