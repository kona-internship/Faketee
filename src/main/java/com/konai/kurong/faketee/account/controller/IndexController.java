package com.konai.kurong.faketee.account.controller;

import com.konai.kurong.faketee.account.util.Role;
import com.konai.kurong.faketee.auth.LoginUser;
import com.konai.kurong.faketee.auth.dto.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(@LoginUser SessionUser sessionUser){

        if(sessionUser != null && sessionUser.getRole().equals(Role.USER)){
            return "redirect:http://localhost:8080/account/set-auth";
        }
        return "/account/home";
    }
}
