package com.konai.kurong.faketee.draft.controller;

import com.konai.kurong.faketee.draft.service.DraftService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@Slf4j
@RequestMapping("/corporation/{corId}/draft")
public class DraftController {

    private final DraftService draftService;

    @GetMapping("/apvl-list")
    public String apvlWaitList() {
        return "draft/apvlWaitList";
    }

    @GetMapping("/done-list")
    public String reqDoneList() {
        return "draft/reqDoneList";
    }

    @GetMapping("/req-list")
    public String reqList() {
        return "draft/reqList";
    }

    @GetMapping("/req/{draftId}")
    public String getReqDetail(@PathVariable("draftId") Long draftId, Model model){
        model.addAttribute("draft", draftService.getDraft(draftId));
        return "draft/reqDetail";
    }

    @GetMapping("/apvl/{draftId}")
    public String getApvlDetail(@PathVariable("draftId") Long draftId, Model model){
        model.addAttribute("draft", draftService.getDraft(draftId));
        return "draft/apvlDetail";
    }
}
