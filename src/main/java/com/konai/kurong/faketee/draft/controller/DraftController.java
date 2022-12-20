package com.konai.kurong.faketee.draft.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@Slf4j
@RequestMapping("/corporation/{corId}/draft")
public class DraftController {

    @GetMapping("/apvl-list")
    public String registerEmp() {
        return "draft/apvlWaitList";
    }
}
