package com.konai.kurong.faketee.location.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class LocationController {

    @GetMapping("/corporation/{corId}/loc")
    public String registerLoc(){
        return "location/add_location";
    }
}
