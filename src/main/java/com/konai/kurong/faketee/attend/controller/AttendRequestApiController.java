package com.konai.kurong.faketee.attend.controller;

import com.konai.kurong.faketee.attend.dto.AttendRequestDeleteDto;
import com.konai.kurong.faketee.attend.dto.AttendRequestSaveDto;
import com.konai.kurong.faketee.attend.dto.AttendRequestUpdateDto;
import com.konai.kurong.faketee.attend.service.AttendRequestService;
import com.konai.kurong.faketee.attend.service.AttendService;
import com.konai.kurong.faketee.auth.LoginUser;
import com.konai.kurong.faketee.auth.dto.SessionUser;
import com.konai.kurong.faketee.employee.dto.EmployeeResponseDto;
import com.konai.kurong.faketee.employee.entity.Employee;
import com.konai.kurong.faketee.employee.service.EmployeeService;
import com.konai.kurong.faketee.employee.utils.EmpAuth;
import com.konai.kurong.faketee.employee.utils.EmpRole;
import com.konai.kurong.faketee.employee.utils.ReqEmp;
import com.konai.kurong.faketee.employee.utils.ReqEmpInfo;
import com.konai.kurong.faketee.schedule.dto.ScheduleInfoResponseDto;
import com.konai.kurong.faketee.schedule.service.ScheduleInfoService;
import com.konai.kurong.faketee.utils.exception.custom.attend.request.NoSchInfoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/corporation/{corId}/atd/req")
public class AttendRequestApiController {

    private final AttendRequestService attendRequestService;

    private final ScheduleInfoService scheduleInfoService;
    private final AttendService attendService;

    @GetMapping("/create/check-date")
    public ResponseEntity<?> createAtdRecord(@RequestParam("selectedDate") String date) {
        return ResponseEntity.ok(attendRequestService.checkAtdRecord(date));
    }

    @GetMapping("/create/set-time/sch-info")
    @EmpAuth(role = EmpRole.EMPLOYEE, onlyLowDep = false)
    public ResponseEntity<?> createSetTimeSchInfo(String date,
                                                  @PathVariable(name = "corId") Long corId,
                                                  @ReqEmp ReqEmpInfo empInfo) {
//
//        List<ScheduleInfoResponseDto> responseDto = scheduleInfoService.getScheduleByDateAndEmp(date, corId, empInfo.getId())
//                .stream()
//                .map(ScheduleInfoResponseDto::convertToDto)
//                .collect(Collectors.toList());

        ScheduleInfoResponseDto responseDto = ScheduleInfoResponseDto.convertToDto(scheduleInfoService.getScheduleByDateAndEmp(date, corId, empInfo.getId()));

        if(responseDto == null) {
            throw new NoSchInfoException();
        } else {
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }
    }

    @PostMapping("/create")
    @EmpAuth(role = EmpRole.EMPLOYEE, onlyLowDep = false)
    public ResponseEntity<?> createAtdReq(@RequestBody AttendRequestSaveDto requestDto,
                                          @ReqEmp ReqEmpInfo empInfo) {
        requestDto.setReqEmpId(empInfo.getId());
        attendRequestService.createAttendRequest(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/update")
    @EmpAuth(role = EmpRole.EMPLOYEE, onlyLowDep = false)
    public ResponseEntity<?> updateAtdReq(@RequestBody AttendRequestUpdateDto requestDto,
                                          @ReqEmp ReqEmpInfo empInfo) {
        requestDto.setReqEmpId(empInfo.getId());
        attendRequestService.updateAttendRequest(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/delete")
    @EmpAuth(role = EmpRole.EMPLOYEE, onlyLowDep = false)
    public ResponseEntity<?> deleteAtdReq(@RequestBody AttendRequestDeleteDto requestDto,
                                          @ReqEmp ReqEmpInfo empInfo) {
        requestDto.setReqEmpId(empInfo.getId());
        attendRequestService.deleteAttendRequest(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/update/set-month")
    public ResponseEntity<?> updateSetMonth(@RequestParam("month") int month) {
        return ResponseEntity.ok(attendService.getAttendByMonth(month));
    }

    @EmpAuth(role = EmpRole.EMPLOYEE)
    @GetMapping("/load/apv-emp")
    public ResponseEntity<?> loadApvlEmpList(@PathVariable(name = "corId") Long corId, @ReqEmp ReqEmpInfo empInfo) {
        log.info("22");
        List<EmployeeResponseDto> responseDtos = attendRequestService.loadApvlEmp(corId, empInfo.getId());
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }
}
