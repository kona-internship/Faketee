package com.konai.kurong.faketee.location.controller;

import com.konai.kurong.faketee.employee.utils.EmpAuth;
import com.konai.kurong.faketee.employee.utils.EmpRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String listLoc() {
        return "location/list";
    }
}
