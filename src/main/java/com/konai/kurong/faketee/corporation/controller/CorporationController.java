package com.konai.kurong.faketee.corporation.controller;

import com.konai.kurong.faketee.corporation.service.CorporationService;
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
    public String home(@PathVariable(name = "corId") Long corId,
                       Model model){

        model.addAttribute("corporation", corporationService.findById(corId));
        return "corporation/home";
    }

}
