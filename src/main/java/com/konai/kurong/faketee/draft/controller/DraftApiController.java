package com.konai.kurong.faketee.draft.controller;

import com.konai.kurong.faketee.draft.dto.DraftCancelRequestDto;
import com.konai.kurong.faketee.draft.service.DraftService;
import com.konai.kurong.faketee.employee.utils.EmpAuth;
import com.konai.kurong.faketee.employee.utils.EmpRole;
import com.konai.kurong.faketee.employee.utils.ReqEmp;
import com.konai.kurong.faketee.employee.utils.ReqEmpInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/corporation/{corId}/draft")
public class DraftApiController {

    private final DraftService draftService;

    @EmpAuth(role = EmpRole.EMPLOYEE)
    @GetMapping("/req-list")
    public ResponseEntity<?> getMyReqDraftList(
            @ReqEmp ReqEmpInfo empInfo) {
        return new ResponseEntity<>(draftService.getReqDraftList(empInfo.getId()), HttpStatus.OK);
    }

    @EmpAuth(role = EmpRole.EMPLOYEE)
    @GetMapping("/apvl-list")
    public ResponseEntity<?> getApvlDraftList(
            @ReqEmp ReqEmpInfo empInfo) {
        return new ResponseEntity<>(draftService.getApvlDraftList(empInfo.getId()), HttpStatus.OK);
    }

    @EmpAuth(role = EmpRole.EMPLOYEE)
    @GetMapping("/done-list")
    public ResponseEntity<?> getDoneDraftList(
            @ReqEmp ReqEmpInfo empInfo) {
        return new ResponseEntity<>(draftService.getDoneDraftList(empInfo.getId()), HttpStatus.OK);
    }

    @EmpAuth(role = EmpRole.EMPLOYEE)
    @PostMapping("/cancel")
    public ResponseEntity<?> cancelDraft(
            @Valid @RequestBody DraftCancelRequestDto requestDto) {
        draftService.cancelDraft(requestDto.getDraftId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
