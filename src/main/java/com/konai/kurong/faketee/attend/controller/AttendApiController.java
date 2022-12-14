package com.konai.kurong.faketee.attend.controller;

import com.konai.kurong.faketee.attend.service.AttendService;
import com.konai.kurong.faketee.auth.LoginUser;
import com.konai.kurong.faketee.auth.dto.SessionUser;
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

    @GetMapping("/load")
    public ResponseEntity<?> loadHome(@PathVariable(name = "corId") Long corId,
                                      @RequestParam("date") String date,
                                      @LoginUser SessionUser user) {

        return new ResponseEntity<>(attendService.getUserScheduleInfo(corId, date, user.getId()), HttpStatus.OK);
    }
}
