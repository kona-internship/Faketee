package com.konai.kurong.faketee.schedule.controller;

import com.konai.kurong.faketee.department.service.DepartmentService;
import com.konai.kurong.faketee.position.service.PositionService;
import com.konai.kurong.faketee.schedule.service.ScheduleService;
import com.konai.kurong.faketee.schedule.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/corporation/{corId}/template")
@Controller
public class TemplateController {

    private final TemplateService templateService;
    private final ScheduleService scheduleService;
    private final DepartmentService departmentService;
    private final PositionService positionService;

    @GetMapping("/list")
    public String templateList(){

        return "schedule/template-list";
    }

    @GetMapping("/new")
    public String newTemplate(){

        return "schedule/template-new";
    }
}
