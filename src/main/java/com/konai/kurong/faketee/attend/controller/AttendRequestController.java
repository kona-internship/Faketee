package com.konai.kurong.faketee.attend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/corporation/{corId}/atd/req")
public class AttendRequestController {
    @GetMapping("/create")
    public String create() {
        return "attend/request/create";
    }

    @GetMapping("/create/set-time")
    public String createSetTime(Model model, @RequestParam String selectedDate) {
        model.addAttribute("selectedDate", selectedDate);
        return "attend/request/create-setTime";
    }

    @GetMapping("/create/set-apvl")
    public String createSetApvl(Model model, @RequestParam String selectedDate, @RequestParam String startTime,
                                @RequestParam String endTime, @RequestParam Long schInfoId, @RequestParam String schInfo) {
        model.addAttribute("selectedDate", selectedDate);
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        model.addAttribute("schInfoId", schInfoId);
        model.addAttribute("schInfo", schInfo);
        return "attend/request/create-setApvl";
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