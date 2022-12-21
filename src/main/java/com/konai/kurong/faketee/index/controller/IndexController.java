package com.konai.kurong.faketee.index.controller;

import com.konai.kurong.faketee.auth.LoginUser;
import com.konai.kurong.faketee.auth.dto.SessionUser;
import com.konai.kurong.faketee.index.service.IndexService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class IndexController {

    private final IndexService indexService;

    @GetMapping("/")
    public String index(@LoginUser SessionUser sessionUser){

        return indexService.splitUri(sessionUser);
    }

    @GetMapping("/user-auth")
    public String setAuth(){

        return "/index/user-auth";
    }

    @GetMapping("/join-corporation")
    public String joinCorporation(){

        return "/index/join-corporation";
    }

}
