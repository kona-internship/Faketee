package com.konai.kurong.faketee.schedule.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/corporation/{cordId}/template")
@Controller
public class TemplateController {

    @GetMapping("/list")
    public String templateList(){

        return "schedule/template-list";
    }

    @GetMapping("/new")
    public String newTemplate(){

        return "schedule/template-new";
    }
}
