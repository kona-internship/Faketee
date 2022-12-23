package com.konai.kurong.faketee.vacation.controller;


import com.konai.kurong.faketee.account.service.UserService;
import com.konai.kurong.faketee.auth.LoginUser;
import com.konai.kurong.faketee.auth.dto.SessionUser;
import com.konai.kurong.faketee.employee.utils.ReqEmp;
import com.konai.kurong.faketee.employee.utils.ReqEmpInfo;
import com.konai.kurong.faketee.vacation.dto.VacInfoResponseDto;
import com.konai.kurong.faketee.vacation.dto.VacRemainResponseDto;
import com.konai.kurong.faketee.vacation.dto.VacTypeResponseDto;
import com.konai.kurong.faketee.vacation.service.VacInfoService;
import com.konai.kurong.faketee.vacation.service.VacRequestService;
import com.konai.kurong.faketee.vacation.service.VacTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/corporation/{corId}/vac/req")
@RestController
public class VacRequestApiController {

    private final VacRequestService vacRequestService;
    private final VacInfoService vacInfoService;
    private final VacTypeService vacTypeService;
    private final UserService userService;

    @GetMapping("/remaining")
    public ResponseEntity<?> getRemaining(@RequestParam(name = "type") Long type,
                                          @LoginUser SessionUser sessionUser,
                                          @PathVariable(name = "corId") Long corId){

        VacTypeResponseDto vacTypeResponseDto= vacTypeService.findById(type);
        VacInfoResponseDto vacInfoResponseDto = vacInfoService.findByEmpAndVacGroup(userService.findEmployeeId(sessionUser, corId), vacTypeResponseDto.getVacGroupResponseDto().getId());

        log.info("remaining called========================================================");
        return ResponseEntity.ok(VacRemainResponseDto.builder()
                        .vacGroup(vacTypeResponseDto.getVacGroupResponseDto().getName())
                        .remain(vacInfoResponseDto.getRemaining())
                        .sub(vacTypeResponseDto.getSub())
                        .build());
    }

}
