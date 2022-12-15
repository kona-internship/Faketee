package com.konai.kurong.faketee.schedule.controller;

import com.konai.kurong.faketee.department.service.DepartmentService;
import com.konai.kurong.faketee.employee.utils.EmpAuth;
import com.konai.kurong.faketee.employee.utils.EmpRole;
import com.konai.kurong.faketee.position.service.PositionService;
import com.konai.kurong.faketee.schedule.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@RequestMapping("/corporation/{corId}/temp")
@Controller
public class TemplateController {

    private final TemplateService templateService;
    private final DepartmentService departmentService;
    private final PositionService positionService;

    @EmpAuth(role = EmpRole.GENERAL_MANAGER)
    @GetMapping()
    public String templates(){

        return "schedule/template-list";
    }

    @EmpAuth(role = EmpRole.GENERAL_MANAGER)
    @GetMapping("/new")
    public String newTemplate(){

        return "schedule/template-new";
    }

    @EmpAuth(role = EmpRole.GENERAL_MANAGER)
    @GetMapping("/detail")
    public String templateDetails(@RequestParam Long tmpId, Model model){

        model.addAttribute("responseDto", templateService.loadDetails(tmpId));
        return "schedule/template-detail";
    }
}
