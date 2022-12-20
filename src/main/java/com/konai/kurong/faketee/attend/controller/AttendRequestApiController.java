package com.konai.kurong.faketee.attend.controller;

import com.konai.kurong.faketee.attend.service.AttendRequestService;
import com.konai.kurong.faketee.auth.LoginUser;
import com.konai.kurong.faketee.auth.dto.SessionUser;
import com.konai.kurong.faketee.schedule.dto.ScheduleInfoResponseDto;
import com.konai.kurong.faketee.schedule.service.ScheduleInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/corporation/{corId}/atd/req")
public class AttendRequestApiController {

    private final AttendRequestService attendRequestService;

    private final ScheduleInfoService scheduleInfoService;

    @GetMapping("/create/check-date")
    public ResponseEntity<?> createAtdRecord(@RequestParam("selectedDate") String date) {
        log.info("###########AttendRequestApiController createAtdRecord date : " + date);
        return ResponseEntity.ok(attendRequestService.checkAtdRecord(date));
    }

    @GetMapping("/create/set-time/sch-info")
    public ResponseEntity<?> createSetTimeSchInfo(String date,
                                                  @PathVariable(name = "corId") Long corId,
                                                  @LoginUser SessionUser user) {
        return new ResponseEntity<>(ScheduleInfoResponseDto.convertToDto(scheduleInfoService.getSchByDateAndEmp(date, corId, user.getId())), HttpStatus.OK);
    }
}
