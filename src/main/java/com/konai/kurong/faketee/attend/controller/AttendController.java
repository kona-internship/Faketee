package com.konai.kurong.faketee.attend.controller;

import com.konai.kurong.faketee.auth.LoginUser;
import com.konai.kurong.faketee.auth.dto.SessionUser;
import com.konai.kurong.faketee.employee.utils.EmpAuth;
import com.konai.kurong.faketee.employee.utils.EmpRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/corporation/{corId}/atd")
public class AttendController {
    @EmpAuth(role = EmpRole.EMPLOYEE)
    @GetMapping("/reg")
    public String goRegisterAtd(Model model, @RequestParam String flag){
        if(flag.equals("on")){
            model.addAttribute("state", "출근");
        }else{
            model.addAttribute("state", "퇴근");
        }

        return "attend/attend-click";
    }
}
