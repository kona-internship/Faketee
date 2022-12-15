package com.konai.kurong.faketee.vacation.controller;

import com.konai.kurong.faketee.employee.utils.EmpAuth;
import com.konai.kurong.faketee.employee.utils.EmpRole;
import com.konai.kurong.faketee.vacation.service.VacTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/corporation/{corId}/vac/type")
@Controller
public class VacTypeController {

    private final VacTypeService vacTypeService;

    @EmpAuth(role = EmpRole.GENERAL_MANAGER)
    @GetMapping()
    public String vacTypes(){

        return "/vacation/vtype";
    }

    @EmpAuth(role = EmpRole.GENERAL_MANAGER)
    @GetMapping("/form")
    public String newType(){

        return "/vacation/vtype-new";
    }

}
