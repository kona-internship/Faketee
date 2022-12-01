package com.konai.kurong.faketee.schedule.controller;

import com.konai.kurong.faketee.position.dto.PositionSaveRequestDto;
import com.konai.kurong.faketee.schedule.dto.ScheduleTypeSaveRequestDto;
import com.konai.kurong.faketee.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/corporation/{corId}/sch")
public class ScheduleApiController {

    private final ScheduleService scheduleService;

    @PostMapping("/type/list")
    public ResponseEntity<?> getSchTypeList(@PathVariable(name = "corId") Long corId) {

        return new ResponseEntity<>(scheduleService.getSchTypeList(corId), HttpStatus.OK);
    }

    @PostMapping("/type")
    public ResponseEntity<?> registerSchType(@PathVariable(name = "corId") Long corId,
                                             @Valid @RequestBody ScheduleTypeSaveRequestDto requestDto) {

        scheduleService.registerSchType(corId, requestDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/type/delete/{typeId}")
    public ResponseEntity<?> removeSchType(@PathVariable(name = "corId") Long corId,
                                            @PathVariable(name = "typeId") Long typeId){
        scheduleService.removeSchType(corId, typeId);

        return new ResponseEntity<>(scheduleService.getSchTypeList(corId), HttpStatus.OK);
    }
}
