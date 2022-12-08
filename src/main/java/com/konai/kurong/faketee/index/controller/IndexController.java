package com.konai.kurong.faketee.index.controller;

import com.konai.kurong.faketee.account.util.Role;
import com.konai.kurong.faketee.auth.LoginUser;
import com.konai.kurong.faketee.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(@LoginUser SessionUser sessionUser){

        if(sessionUser != null && sessionUser.getRole().equals(Role.USER)){
            return "redirect:http://localhost:8080/set-auth";
        }
        return "/index/home";
    }

    @GetMapping("/set-auth")
    public String setAuth(){

        return "/index/set-auth";
    }

    @GetMapping("/join-cor")
    public String joinCor(){

        return "/index/join-cor";
    }

}
