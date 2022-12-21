package com.konai.kurong.faketee.draft.controller;

import com.konai.kurong.faketee.draft.service.DraftService;
import com.konai.kurong.faketee.employee.utils.EmpAuth;
import com.konai.kurong.faketee.employee.utils.EmpRole;
import com.konai.kurong.faketee.employee.utils.ReqEmp;
import com.konai.kurong.faketee.employee.utils.ReqEmpInfo;
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

    @GetMapping("/wait")
    @EmpAuth(role = EmpRole.EMPLOYEE)
    public String apvlWaitList() {
        return "draft/apvlWaitList";
    }

    @GetMapping("/done")
    @EmpAuth(role = EmpRole.EMPLOYEE)
    public String reqDoneList(@ReqEmp ReqEmpInfo reqEmpInfo, Model model) {
        model.addAttribute("isManager", !reqEmpInfo.getRole().equals(EmpRole.EMPLOYEE));
        return "draft/reqDoneList";
    }

    @GetMapping("/req")
    @EmpAuth(role = EmpRole.EMPLOYEE)
    public String reqList(@ReqEmp ReqEmpInfo reqEmpInfo, Model model) {
        model.addAttribute("isManager", !reqEmpInfo.getRole().equals(EmpRole.EMPLOYEE));
        return "draft/reqList";
    }

    @GetMapping("/req/{draftId}")
    @EmpAuth(role = EmpRole.EMPLOYEE)
    public String getReqDetail(@PathVariable("draftId") Long draftId, Model model){
        model.addAttribute("draft", draftService.getDraft(draftId));
        return "draft/reqDetail";
    }

    @GetMapping("/apvl/{draftId}")
    @EmpAuth(role = EmpRole.EMPLOYEE)
    public String getApvlDetail(@PathVariable("draftId") Long draftId, Model model){
        model.addAttribute("draft", draftService.getDraft(draftId));
        return "draft/apvlDetail";
    }
}
