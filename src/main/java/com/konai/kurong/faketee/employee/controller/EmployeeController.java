package com.konai.kurong.faketee.employee.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/corporation/{corId}/emp")
public class EmployeeController {

    @GetMapping("/register")
    public String registerEmp() {
        return "employee/register";
    }

    @GetMapping("/update")
    public String updateEmp() {
        return "employee/update";
    }
}
