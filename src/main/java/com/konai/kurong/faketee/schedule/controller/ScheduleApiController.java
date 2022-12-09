package com.konai.kurong.faketee.schedule.controller;

import com.konai.kurong.faketee.schedule.dto.ScheduleTypeSaveRequestDto;
import com.konai.kurong.faketee.schedule.service.ScheduleInfoService;
import com.konai.kurong.faketee.schedule.service.ScheduleTypeService;
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

    private final ScheduleTypeService scheduleTypeService;
    private final ScheduleInfoService scheduleInfoService;

    @PostMapping("/type/list")
    public ResponseEntity<?> getSchTypeList(@PathVariable(name = "corId") Long corId) {

        return new ResponseEntity<>(scheduleTypeService.getSchTypeList(corId), HttpStatus.OK);
    }

    @PostMapping("/type")
    public ResponseEntity<?> registerSchType(@PathVariable(name = "corId") Long corId,
                                             @Valid @RequestBody ScheduleTypeSaveRequestDto requestDto) {

        scheduleTypeService.registerSchType(corId, requestDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/type/delete/{typeId}")
    public ResponseEntity<?> removeSchType(@PathVariable(name = "corId") Long corId,
                                            @PathVariable(name = "typeId") Long typeId){
        scheduleTypeService.removeSchType(corId, typeId);

        return new ResponseEntity<>(scheduleTypeService.getSchTypeList(corId), HttpStatus.OK);
    }

    @PostMapping("before/reg")
    public ResponseEntity<?> getList(@PathVariable(name = "corId") Long corId){
        return new ResponseEntity<>(scheduleInfoService.getAllRelationWithTemp(corId), HttpStatus.OK);
    }
}
