package com.konai.kurong.faketee.employee.controller;

import com.konai.kurong.faketee.employee.dto.EmployeeResponseDto;
import com.konai.kurong.faketee.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@Slf4j
@RequestMapping("/corporation/{corId}/emp")
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping()
    public String emp() {
        return "employee/list";
    }

    @GetMapping("/register")
    public String registerEmp() {

        return "employee/register";
    }

    @GetMapping("/update/{empId}")
    public String updateEmp(@PathVariable(name = "empId") Long empId, Model model) {
        EmployeeResponseDto responseDto = employeeService.getEmployeeResponseDto(empId);
        log.info("employeeController updateEmp : " + responseDto.toString());
        model.addAttribute("emp", responseDto);
        return "employee/update";
    }

//    합류 초대 다시 보내기
//    val 이 W 와 F 상태에서만 합류 초대를 다시 받을 수 있음
    @GetMapping("/reSend/{empId}")
    public String reSendEmp(@PathVariable(name = "empId") Long empId, Model model) {
        String val = employeeService.findEntityById(empId).getVal();
        if(val.equals("W") || val.equals("F")) {
            model.addAttribute("id", empId);
            return "employee/reSend";
        } else {
            return "employee/reSend-error";
        }
    }
}
