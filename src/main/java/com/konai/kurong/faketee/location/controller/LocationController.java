package com.konai.kurong.faketee.location.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/corporation/{corId}/loc")
public class LocationController {

    @GetMapping()
    public String registerLoc() {
        return "location/register";
    }

    @GetMapping("/list")
    public String listLoc() {
        return "location/list";
    }
}
