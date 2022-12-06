package com.konai.kurong.faketee.department.controller;

import com.konai.kurong.faketee.department.dto.DepartmentResponseDto;
import com.konai.kurong.faketee.department.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/corporation/{corId}/dep")
public class DepartmentController {

    private final DepartmentService departmentService;

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
    public String modifyDepPage(@PathVariable(name = "depId") Long depId, Model model){
        DepartmentResponseDto responseDto = departmentService.getDepWithoutSuper(depId);
        model.addAttribute("dep", responseDto);
        return "department/modification";
    }

    @GetMapping("/{depId}")
    public String goDetailDepPage(){

        return "department/detail";
    }
}
