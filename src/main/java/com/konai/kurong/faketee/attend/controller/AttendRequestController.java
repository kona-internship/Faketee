package com.konai.kurong.faketee.attend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/corporation/{corId}/atd/req")
public class AttendRequestController {
    @GetMapping("/create")
    public String create() {
        return "attend/request/create";
    }

    @GetMapping("/update")
    public String update() {
        return "attend/request/update";
    }

    @GetMapping("/delete")
    public String delete() {
        return "attend/request/delete";
    }
}