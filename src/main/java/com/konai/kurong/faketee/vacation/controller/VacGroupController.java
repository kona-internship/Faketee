package com.konai.kurong.faketee.vacation.controller;

import com.konai.kurong.faketee.employee.utils.EmpAuth;
import com.konai.kurong.faketee.employee.utils.EmpRole;
import com.konai.kurong.faketee.vacation.service.VacGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@RequestMapping("/corporation/{corId}/vac/group")
@Controller
public class VacGroupController {

    private final VacGroupService vacGroupService;

    @EmpAuth(role = EmpRole.EMPLOYEE)
    @GetMapping()
    public String vacGroups(){

        return "vacation/vgroup";
    }

    @EmpAuth(role = EmpRole.GENERAL_MANAGER)
    @GetMapping("/form")
    public String newGroup(){

        return "/vacation/vgroup-new";
    }

    @EmpAuth(role = EmpRole.EMPLOYEE)
    @GetMapping("/details")
    public String details(@RequestParam(name = "groupId") Long groupId, Model model){

        model.addAttribute("responseDto", vacGroupService.findById(groupId));
        return "/vacation/vgroup-details";
    }
}
