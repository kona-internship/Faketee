package com.konai.kurong.faketee.account.controller;

import com.konai.kurong.faketee.auth.LoginUser;
import com.konai.kurong.faketee.auth.PrincipalDetails;
import com.konai.kurong.faketee.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RequestMapping("/account")
@RequiredArgsConstructor
@Controller
public class MyPageController {

    private final HttpServletRequest httpServletRequest;

    @GetMapping("/mypage")
    public String myPage(Model model) {

//        log.info("sessionUser =========================" + sessionUser.getEmail());

        log.info("mypage들어간다");
        SessionUser sessionUser1 = (SessionUser) httpServletRequest.getSession().getAttribute("user");
//        System.out.println(sessionUser.getEmail());
        log.info("sessionUser1 =========================" + sessionUser1.getEmail());

        model.addAttribute("email", sessionUser1.getEmail());
        model.addAttribute("name", sessionUser1.getName());
        return "account/mypage";
    }

    @GetMapping("/mypage/set-info")
    public String setInfo(Model model, @LoginUser SessionUser sessionUser) {

        if(sessionUser.getType().getKey().equals("TYPE_GENERAL")) {
            model.addAttribute("email", sessionUser.getEmail());
            model.addAttribute("name", sessionUser.getName());
            return "account/set-info";
        } else {
            return "account/mypage-error";
        }
    }
}
