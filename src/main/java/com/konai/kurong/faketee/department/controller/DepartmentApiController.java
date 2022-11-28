package com.konai.kurong.faketee.department.controller;

import com.konai.kurong.faketee.department.dto.DepartmentRemoveRequestDto;
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
    public ResponseEntity<?> getDepList(@PathVariable(name = "corId") Long corId){

        return new ResponseEntity<>(departmentService.getDepList(corId), HttpStatus.OK);
    }

    @PostMapping("/remove/{depId}")
    public ResponseEntity<?> removeDep(@PathVariable(name = "corId") Long corId,
                                       @RequestBody DepartmentRemoveRequestDto requestDto){

        log.info(requestDto.toString());
        log.info(String.valueOf(requestDto.getRemoveDepList().get(1).get(3L)));
        //여기서 객체에 리스트 두개 롱타입으로 조직id와 조직 level의 값을 전달받는다.
        departmentService.removeDep(corId, requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
