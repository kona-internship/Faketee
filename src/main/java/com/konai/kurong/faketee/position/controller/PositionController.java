package com.konai.kurong.faketee.position.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/corporation/{corId}/pos")
public class PositionController {

    @GetMapping()
    public String registerPos(){
        return "position/registration";
    }

    @GetMapping("/{posId}/{posName}")
    public String goModiPosPage(@PathVariable(name = "posId") Long posId, @PathVariable(name = "posName") String posName, Model model){
        model.addAttribute("posId", posId);
        model.addAttribute("posName", posName);

        return "position/modification";
    }
}
