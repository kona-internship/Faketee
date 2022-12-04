package com.konai.kurong.faketee.employee.controller;

import com.konai.kurong.faketee.department.dto.DepartmentSaveRequestDto;
import com.konai.kurong.faketee.employee.dto.EmployeeModifyRequestDto;
import com.konai.kurong.faketee.employee.dto.EmployeeReSendRequestDto;
import com.konai.kurong.faketee.employee.dto.EmployeeSaveRequestDto;
import com.konai.kurong.faketee.employee.entity.Employee;
import com.konai.kurong.faketee.employee.service.EmployeeService;
import com.konai.kurong.faketee.position.service.PositionService;
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
        log.info("EmployeeApiController registerEmp");
        employeeService.registerEmployee(corId, requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

//    직원 수정하기
    @PostMapping("/update/{employeeInfoId}/{employeeId}")
    public ResponseEntity<?> updateEmp(@PathVariable(name = "corId") Long corId,
                                       @PathVariable(name = "employeeInfoId") Long employeeInfoId,
                                       @PathVariable(name = "employeeId") Long employeeId,
                                       @Valid @RequestBody EmployeeModifyRequestDto requestDto) {
        employeeService.updateEmployee(corId, employeeInfoId, employeeId, requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    직원 비활성화
    @GetMapping("/deactivate/{employeeId}")
    public ResponseEntity<?> deactivateEmp(@PathVariable(name = "corId") Long corId,
                                           @PathVariable(name = "employeeId") Long employeeId) {
        employeeService.deactivateEmployee(corId, employeeId);
        return new ResponseEntity<>(HttpStatus.OK);
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
