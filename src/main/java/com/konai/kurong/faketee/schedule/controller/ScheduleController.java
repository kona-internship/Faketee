package com.konai.kurong.faketee.schedule.controller;

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

    @GetMapping()
    public String registerSchType() {
        return "schedule/typeRegistration";
    }

    @GetMapping("/type")
    public String typeList() {
        return "schedule/typeList";
    }

    @GetMapping("/reg")
    public String registerSch() {
        return "schedule/schedule-new";
    }

    @GetMapping("/list")
    public String goSchList() {
        return "schedule/schedule-list";
    }
}
