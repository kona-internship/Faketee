package com.konai.kurong.faketee.corporation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class CorporationController {
    @GetMapping("/corporation")
    public String registerCor(){
        return "corporation/register";
    }
}
