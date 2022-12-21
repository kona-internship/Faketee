package com.konai.kurong.faketee.attend.controller;

import com.konai.kurong.faketee.attend.service.AttendService;
import com.konai.kurong.faketee.auth.LoginUser;
import com.konai.kurong.faketee.auth.dto.SessionUser;
import com.konai.kurong.faketee.employee.utils.EmpAuth;
import com.konai.kurong.faketee.employee.utils.EmpRole;
import com.konai.kurong.faketee.employee.utils.ReqEmp;
import com.konai.kurong.faketee.employee.utils.ReqEmpInfo;
import com.konai.kurong.faketee.location.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/corporation/{corId}/atd")
public class AttendApiController {
    private final AttendService attendService;
    private final LocationService locationService;

    @EmpAuth(role = EmpRole.EMPLOYEE)
    @GetMapping("/load")
    public ResponseEntity<?> loadHome(@PathVariable(name = "corId") Long corId,
                                      @RequestParam("date") String date, @ReqEmp ReqEmpInfo empInfo) {

        return new ResponseEntity<>(attendService.getUserScheduleInfo(corId, date, empInfo.getId()), HttpStatus.OK);
    }
    @GetMapping("/load/atd/loc")
    public ResponseEntity<?> loadHome(@PathVariable(name = "corId") Long corId, @ReqEmp ReqEmpInfo empInfo) {

        return new ResponseEntity<>(locationService.getAtdLocList(corId, empInfo.getId()), HttpStatus.OK);
    }

    @EmpAuth(role = EmpRole.EMPLOYEE)
    @GetMapping("/reg")
    public ResponseEntity<?> clickAtd(@PathVariable(name = "corId") Long corId,
                                         @RequestParam("onoff") String onOff,
                                      @ReqEmp ReqEmpInfo empInfo){

        attendService.clickAtd(corId, empInfo.getId(), onOff);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
