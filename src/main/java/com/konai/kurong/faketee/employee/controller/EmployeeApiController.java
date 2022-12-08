package com.konai.kurong.faketee.employee.controller;

import com.konai.kurong.faketee.auth.LoginUser;
import com.konai.kurong.faketee.auth.dto.SessionUser;
import com.konai.kurong.faketee.employee.dto.EmployeeJoinRequestDto;
import com.konai.kurong.faketee.employee.dto.EmployeeUpdateRequestDto;
import com.konai.kurong.faketee.employee.dto.EmployeeReSendRequestDto;
import com.konai.kurong.faketee.employee.dto.EmployeeSaveRequestDto;
import com.konai.kurong.faketee.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/corporation/{corId}/emp")
public class EmployeeApiController {
    private final EmployeeService employeeService;

//    직원 저장하기
    @PostMapping("/register")
    public ResponseEntity<?> registerEmp(
                                        @PathVariable(name = "corId") Long corId,
                                         @Valid @RequestBody EmployeeSaveRequestDto requestDto) {
        employeeService.registerEmployee(corId, requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

//    직원 수정하기
    @PostMapping("/update/{employeeId}")
    public ResponseEntity<?> updateEmp(@PathVariable(name = "corId") Long corId,
                                       @PathVariable(name = "employeeId") Long employeeId,
                                       @Valid @RequestBody EmployeeUpdateRequestDto requestDto) {
        log.info("EmployeeApiController requestDto : " + requestDto.toString());
        employeeService.updateEmployee(corId, employeeId, requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/join/{employeeId}")
    public ResponseEntity<?> joinEmp(@LoginUser SessionUser user,
                                     @PathVariable(name = "employeeId") Long employeeId,
                                     EmployeeJoinRequestDto requestDto) {
        employeeService.joinEmployee(employeeId, user.getId(), requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    모든 직원 목록 가져오기
    @GetMapping("/list")
    public ResponseEntity<?> getAllEmp(@PathVariable(name = "corId") Long corId) {
        return new ResponseEntity<>(employeeService.getAllEmployee(corId), HttpStatus.OK);
    }

//    직원 비활성화
//    만약에 이미 비활성화 된 직원이라면 forbidden 처리 해두었음
    @GetMapping("/deactivate/{employeeId}")
    public ResponseEntity<?> deactivateEmp(@PathVariable(name = "corId") Long corId,
                                           @PathVariable(name = "employeeId") Long employeeId) {
        if(employeeService.findByEmployeeById(employeeId).getVal() == "F") {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        else {
            employeeService.deactivateEmployee(corId, employeeId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

//    직원 합류 초대 재전송
    @PostMapping("/reSend/{employeeId}")
    public ResponseEntity<?> reSendJoinCode(@PathVariable(name = "corId") Long corId,
                                            @PathVariable(name = "employeeId") Long employeeId,
                                            @Valid @RequestBody EmployeeReSendRequestDto requestDto) {
        employeeService.reSendJoinCode(corId, employeeId, requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
