package com.konai.kurong.faketee.department.controller;

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

    @GetMapping()
    public String listDep(){
        return "department/list";
    }

    @GetMapping("/reg")
    public String registerDep(){
        return "department/registration";
    }

    @GetMapping("/{posId}/{posName}")
    public String goModiDepPage(@PathVariable(name = "posId") Long posId, @PathVariable(name = "posName") String posName, Model model){
        model.addAttribute("posId", posId);
        model.addAttribute("posName", posName);

        return "department/modification";
    }
}
