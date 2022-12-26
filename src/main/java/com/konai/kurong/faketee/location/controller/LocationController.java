package com.konai.kurong.faketee.location.controller;

import com.konai.kurong.faketee.employee.utils.EmpAuth;
import com.konai.kurong.faketee.employee.utils.EmpRole;
import com.konai.kurong.faketee.employee.utils.ReqEmp;
import com.konai.kurong.faketee.employee.utils.ReqEmpInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/corporation/{corId}/loc")
public class LocationController {

    @EmpAuth(role = EmpRole.GENERAL_MANAGER)
    @GetMapping()
    public String registerLoc() {
        return "location/register";
    }

    @EmpAuth(role = EmpRole.EMPLOYEE)
    @GetMapping("/list")
    public String listLoc(@ReqEmp ReqEmpInfo reqEmpInfo,
                          Model model) {

        model.addAttribute("role", reqEmpInfo.getRole().getRole());
        return "location/list";
    }
}
