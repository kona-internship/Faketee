package com.konai.kurong.faketee.department.controller;

import com.konai.kurong.faketee.department.dto.DepartmentResponseDto;
import com.konai.kurong.faketee.department.service.DepartmentService;
import com.konai.kurong.faketee.employee.utils.EmpAuth;
import com.konai.kurong.faketee.employee.utils.EmpRole;
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

    @EmpAuth(role = EmpRole.EMPLOYEE)
    @GetMapping()
    public String listDepPage(){
        return "department/list";
    }

    @EmpAuth(role = EmpRole.GROUP_MANAGER)
    @GetMapping("/reg")
    public String registerDepPage(){
        return "department/registration";
    }

    @EmpAuth(role = EmpRole.GROUP_MANAGER)
    @GetMapping("/remove")
    public String removeDepPage(){
        return "department/remove";
    }

    @EmpAuth(role = EmpRole.GROUP_MANAGER)
    @GetMapping("/{depId}/mod")
    public String modifyDepPage(@PathVariable(name = "depId") Long depId, Model model){
        DepartmentResponseDto responseDto = departmentService.getDepWithoutSuper(depId);
        model.addAttribute("dep", responseDto);
        return "department/modification";
    }

    @EmpAuth(role = EmpRole.GROUP_MANAGER)
    @GetMapping("/{depId}")
    public String goDetailDepPage(){

        return "department/detail";
    }
}
