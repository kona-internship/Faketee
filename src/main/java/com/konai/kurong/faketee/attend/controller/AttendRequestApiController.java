package com.konai.kurong.faketee.attend.controller;

import com.konai.kurong.faketee.attend.service.AttendRequestService;
import com.konai.kurong.faketee.attend.service.AttendService;
import com.konai.kurong.faketee.auth.LoginUser;
import com.konai.kurong.faketee.auth.dto.SessionUser;
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
    public ResponseEntity<?> createSetTimeSchInfo(String date,
                                                  @PathVariable(name = "corId") Long corId,
                                                  @LoginUser SessionUser user) {
        ScheduleInfoResponseDto responseDto = ScheduleInfoResponseDto.convertToDto(scheduleInfoService.getSchByDateAndEmp(date, corId, user.getId()));
        if(responseDto == null) {
            throw new NoSchInfoException();
        } else {
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }
    }

    @GetMapping("/update/set-month")
    public ResponseEntity<?> updateSetMonth(@RequestParam("month") int month) {
        return ResponseEntity.ok(attendService.getAttendByMonth(month));
    }
}
