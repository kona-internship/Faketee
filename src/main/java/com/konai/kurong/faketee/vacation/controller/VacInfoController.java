package com.konai.kurong.faketee.vacation.controller;

import com.konai.kurong.faketee.account.service.UserService;
import com.konai.kurong.faketee.auth.LoginUser;
import com.konai.kurong.faketee.auth.dto.SessionUser;
import com.konai.kurong.faketee.employee.dto.EmployeeResponseDto;
import com.konai.kurong.faketee.employee.service.EmployeeService;
import com.konai.kurong.faketee.employee.utils.EmpAuth;
import com.konai.kurong.faketee.employee.utils.EmpRole;
import com.konai.kurong.faketee.vacation.service.VacInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/corporation/{corId}/vac/info")
@Controller
public class VacInfoController {

    private final VacInfoService vacInfoService;
    private final UserService userService;
    private final EmployeeService employeeService;

    @EmpAuth(role = EmpRole.EMPLOYEE)
    @GetMapping()
    public String vacInfo(@LoginUser SessionUser sessionUser,
                          @PathVariable(name = "corId") Long corId,
                          Model model){

        model.addAttribute("responseDtoList", vacInfoService.loadByEmpId(userService.findEmployeeId(sessionUser, corId)));
        model.addAttribute("empId", userService.findEmployeeId(sessionUser, corId));
        return "/vacation/vinfo-my";
    }

    @EmpAuth(role = EmpRole.GENERAL_MANAGER)
    @GetMapping("/dep")
    public String vacInfoDep(){

        return "/vacation/vinfo-dep";
    }

    @EmpAuth(role = EmpRole.GENERAL_MANAGER)
    @GetMapping("/dep/{depId}")
    public String vacInfoDepId(@PathVariable(name = "depId") Long depId,
                               Model model){

        model.addAttribute("employeeList", employeeService.findByDepId(depId));
        return "/vacation/vinfo-dep-detail";
    }

    @EmpAuth(role = EmpRole.GENERAL_MANAGER)
    @GetMapping("/emp/{empId}")
    public String vacInfoEmp(@PathVariable(name = "empId") Long empId,
                             Model model){

        model.addAttribute("responseDto", employeeService.findById(empId));
        return "/vacation/vinfo-emp";
    }

    @EmpAuth(role = EmpRole.GENERAL_MANAGER)
    @GetMapping("/emp/{empId}/mod")
    public String vacModEmp(@PathVariable(name = "empId") Long empId,
                            Model model){

        model.addAttribute("empId", empId);
        return "/vacation/vinfo-emp-mod";
    }
}
