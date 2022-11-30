package com.konai.kurong.faketee.department.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/corporation/{corId}/dep")
public class DepartmentController {

    @GetMapping()
    public String listDepPage(){
        return "department/list";
    }

    @GetMapping("/reg")
    public String registerDepPage(){
        return "department/registration";
    }

    @GetMapping("/remove")
    public String removeDepPage(){
        return "department/remove";
    }

    @GetMapping("/{depId}/mod")
    public String modifyDepPage(){
        return "department/modification";
    }


    @GetMapping("/{depId}")
    public String goDetailDepPage(){

        return "department/detail";
    }
}
