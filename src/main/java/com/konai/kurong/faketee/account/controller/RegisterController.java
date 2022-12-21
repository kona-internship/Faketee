package com.konai.kurong.faketee.account.controller;

import com.konai.kurong.faketee.account.util.Role;
import com.konai.kurong.faketee.auth.LoginUser;
import com.konai.kurong.faketee.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

import static com.konai.kurong.faketee.utils.URL.INIT_USR_LOGIN_REDIRECT_URL;

@Slf4j
@RequestMapping("/account")
@RequiredArgsConstructor
@Controller
public class RegisterController {

    private final HttpServletRequest httpServletRequest;

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
            return "redirect:http://localhost:8080" + INIT_USR_LOGIN_REDIRECT_URL;
        }
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "account/login-form";
    }

    @GetMapping("/register-form")
    public String registerForm(){

        return "account/register-form";
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
