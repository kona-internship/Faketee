package com.konai.kurong.faketee.corporation.controller;

import com.konai.kurong.faketee.corporation.service.CorporationService;
import com.konai.kurong.faketee.employee.utils.EmpAuth;
import com.konai.kurong.faketee.employee.utils.EmpRole;
import com.konai.kurong.faketee.employee.utils.ReqEmp;
import com.konai.kurong.faketee.employee.utils.ReqEmpInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class CorporationController {

    private final CorporationService corporationService;

    @GetMapping("/corporation")
    public String registerCor() {

        return "corporation/register";
    }

    @GetMapping("/corporation/{corId}")
    @EmpAuth(role = EmpRole.EMPLOYEE)
    public String home(@PathVariable(name = "corId") Long corId,
                       Model model,
                       @ReqEmp ReqEmpInfo reqEmpInfo){

        model.addAttribute("corporation", corporationService.findById(corId));
        model.addAttribute("role", reqEmpInfo.getRole().getRole());
        return "corporation/home";
    }

}
