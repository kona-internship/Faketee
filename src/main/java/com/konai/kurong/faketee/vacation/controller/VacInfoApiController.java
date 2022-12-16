package com.konai.kurong.faketee.vacation.controller;

import com.konai.kurong.faketee.employee.utils.EmpAuth;
import com.konai.kurong.faketee.employee.utils.EmpRole;
import com.konai.kurong.faketee.vacation.service.VacInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/corporation/{corId}/vac/info")
@RestController
public class VacInfoApiController {

    private final VacInfoService vacInfoService;

    @EmpAuth(role = EmpRole.EMPLOYEE)
    @GetMapping(produces = "application/json; charset=utf-8")
    public ResponseEntity<?> findVacInfo(@RequestParam Long empId){

        return ResponseEntity.ok(vacInfoService.loadByEmpId(empId));
    }


}
