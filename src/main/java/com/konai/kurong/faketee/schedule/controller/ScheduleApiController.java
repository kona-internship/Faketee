package com.konai.kurong.faketee.schedule.controller;

import com.konai.kurong.faketee.employee.utils.EmpAuth;
import com.konai.kurong.faketee.employee.utils.EmpRole;
import com.konai.kurong.faketee.employee.utils.ReqEmp;
import com.konai.kurong.faketee.employee.utils.ReqEmpInfo;
import com.konai.kurong.faketee.schedule.dto.ScheduleInfoDepRequestDto;
import com.konai.kurong.faketee.schedule.dto.ScheduleInfoSaveRequestDto;
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

    @EmpAuth(role = EmpRole.EMPLOYEE)
    @PostMapping("/type/list")
    public ResponseEntity<?> getSchTypeList(@PathVariable(name = "corId") Long corId) {

        return new ResponseEntity<>(scheduleTypeService.getSchTypeList(corId), HttpStatus.OK);
    }

    @EmpAuth(role = EmpRole.ADMIN)
    @PostMapping("/type")
    public ResponseEntity<?> registerSchType(@PathVariable(name = "corId") Long corId,
                                             @Valid @RequestBody ScheduleTypeSaveRequestDto requestDto) {

        scheduleTypeService.registerSchType(corId, requestDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @EmpAuth(role = EmpRole.ADMIN)
    @PostMapping("/type/delete/{typeId}")
    public ResponseEntity<?> removeSchType(@PathVariable(name = "corId") Long corId,
                                            @PathVariable(name = "typeId") Long typeId){
        scheduleTypeService.removeSchType(typeId);

        return new ResponseEntity<>(scheduleTypeService.getSchTypeList(corId), HttpStatus.OK);
    }

    @EmpAuth(role = EmpRole.EMPLOYEE)
    @PostMapping("/template/emp")
    public ResponseEntity<?> getEmpByDepAndPos(@PathVariable(name = "corId") Long corId,
                                               @RequestBody ScheduleInfoDepRequestDto requestDto){



        return new ResponseEntity<>(scheduleInfoService.getEmpByDepAndPos(requestDto), HttpStatus.OK);
    }

    @EmpAuth(role = EmpRole.GROUP_MANAGER)
    @PostMapping("/reg")
    public ResponseEntity<?> registerScheduleInfo(@PathVariable(name = "corId") Long corId,
                                               @RequestBody ScheduleInfoSaveRequestDto requestDto){

        scheduleInfoService.registerScheduleInfo(requestDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @EmpAuth(role = EmpRole.EMPLOYEE)
    @GetMapping("/list")
    public ResponseEntity<?> getSchList(@PathVariable(name = "corId") Long corId,
                                        @RequestParam("selectedDate") String date,
                                        @ReqEmp ReqEmpInfo empInfo) {

        return new ResponseEntity<>(scheduleInfoService.getSchListByDate(date, corId, empInfo.getId()), HttpStatus.OK);
    }

    @EmpAuth(role = EmpRole.GROUP_MANAGER)
    @PostMapping("/delete")
    public void deleteSch(@RequestParam Long id){

        scheduleInfoService.deleteSchedule(id);
    }
}
