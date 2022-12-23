package com.konai.kurong.faketee.vacation.controller;

import com.konai.kurong.faketee.vacation.service.VacTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/corporation/{corId}/vac/req")
@Controller
public class VacRequestController {

    private final VacTypeService vacTypeService;

    @GetMapping("/new")
    public String newRequest(){

        return "/vacation/vreq-new";
    }

    @GetMapping("/form")
    public String requestForm(@RequestParam(name = "dates") String dates,
                              @RequestParam(name = "type") Long vacTypeId,
                              Model model){

        model.addAttribute("dates", dates);
        model.addAttribute("vactype", vacTypeService.findById(vacTypeId));
        return "/vacation/vreq-form";
    }
}
