package com.konai.kurong.faketee.schedule.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/corporation/{corId}/sch")
public class ScheduleApiController {
//    @PostMapping("/type/list")
//    public ResponseEntity<?> getSchTypeList(@PathVariable(name = "corId") Long corId){
//
//        return new ResponseEntity<>(positionService.getPosList(corId), HttpStatus.OK);
//    }
}
