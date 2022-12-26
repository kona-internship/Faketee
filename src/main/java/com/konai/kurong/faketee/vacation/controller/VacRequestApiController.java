package com.konai.kurong.faketee.vacation.controller;


import com.konai.kurong.faketee.account.service.UserService;
import com.konai.kurong.faketee.auth.LoginUser;
import com.konai.kurong.faketee.auth.dto.SessionUser;
import com.konai.kurong.faketee.employee.dto.EmployeeSaveRequestDto;
import com.konai.kurong.faketee.employee.entity.Employee;
import com.konai.kurong.faketee.employee.service.EmployeeService;
import com.konai.kurong.faketee.vacation.dto.VacInfoResponseDto;
import com.konai.kurong.faketee.vacation.dto.VacRemainResponseDto;
import com.konai.kurong.faketee.vacation.dto.VacRequestFormDto;
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
    private final EmployeeService employeeService;

    @GetMapping("/remaining")
    public ResponseEntity<?> getRemaining(@RequestParam(name = "type") Long type,
                                          @LoginUser SessionUser sessionUser,
                                          @PathVariable(name = "corId") Long corId){

        VacTypeResponseDto vacTypeResponseDto= vacTypeService.findById(type);
        VacInfoResponseDto vacInfoResponseDto = vacInfoService.findByEmpAndVacGroup(userService.findEmployeeId(sessionUser, corId), vacTypeResponseDto.getVacGroupResponseDto().getId());
        return ResponseEntity.ok(VacRemainResponseDto.builder()
                        .vacGroup(vacTypeResponseDto.getVacGroupResponseDto().getName())
                        .remain(vacInfoResponseDto.getRemaining())
                        .sub(vacTypeResponseDto.getSub())
                        .build());
    }

    @GetMapping("/load/approvals")
    public ResponseEntity<?> loadApprovalLine(@PathVariable(name = "corId") Long corId,
                                              @LoginUser SessionUser sessionUser){

        return ResponseEntity.ok(vacRequestService.findApprovalLine(corId, userService.findEmployeeId(sessionUser, corId)));
    }

    @GetMapping("/load/approvals/admin")
    public ResponseEntity<?> loadApprovalAdmin(@PathVariable(name = "corId") Long corId){

        return ResponseEntity.ok(employeeService.findAdminApproval(corId));
    }

    @PostMapping("/request")
    public ResponseEntity<?> newRequest(@RequestBody VacRequestFormDto vacRequestInfoDto,
                                        @PathVariable(name = "corId") Long corId,
                                        @LoginUser SessionUser sessionUser){

        return ResponseEntity.ok(vacRequestService.newVacationRequest(vacRequestInfoDto, userService.findEmployeeId(sessionUser, corId)));
    }


}
