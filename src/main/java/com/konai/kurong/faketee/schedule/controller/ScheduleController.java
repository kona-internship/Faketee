package com.konai.kurong.faketee.schedule.controller;

import com.konai.kurong.faketee.employee.utils.EmpAuth;
import com.konai.kurong.faketee.employee.utils.EmpRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/corporation/{corId}/sch")
public class ScheduleController {

    @EmpAuth(role = EmpRole.ADMIN)
    @GetMapping()
    public String registerSchType() {
        return "schedule/typeRegistration";
    }

    @EmpAuth(role = EmpRole.EMPLOYEE)
    @GetMapping("/type")
    public String typeList() {
        return "schedule/typeList";
    }

    @EmpAuth(role = EmpRole.GROUP_MANAGER)
    @GetMapping("/reg")
    public String registerSch() {
        return "schedule/schedule-new";
    }

    @EmpAuth(role = EmpRole.EMPLOYEE)
    @GetMapping("/list")
    public String goSchList() {
        return "schedule/schedule-list";
    }
}
