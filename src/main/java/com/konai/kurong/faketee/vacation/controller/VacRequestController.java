package com.konai.kurong.faketee.vacation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/corporation/{corId}/vac/req")
@Controller
public class VacRequestController {

    @GetMapping("/new")
    public String newRequest(){

        return "/vacation/vreq-new";
    }
}
