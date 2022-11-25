package com.konai.kurong.faketee.department.controller;

import com.konai.kurong.faketee.department.dto.DepartmentSaveRequestDto;
import com.konai.kurong.faketee.department.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/corporation/{corId}/dep")
public class DepartmentApiController {

    private final DepartmentService departmentService;

    @PostMapping()
    public ResponseEntity<?> registerDep(@PathVariable(name = "corId") Long corId,
                                         @Valid @RequestBody DepartmentSaveRequestDto requestDto){

        departmentService.registerDepartment(corId, requestDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getPosList(@PathVariable(name = "corId") Long corId){

        return new ResponseEntity<>(departmentService.getDepList(corId), HttpStatus.OK);
    }


}
