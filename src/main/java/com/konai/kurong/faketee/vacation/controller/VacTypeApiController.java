package com.konai.kurong.faketee.vacation.controller;

import com.konai.kurong.faketee.employee.utils.EmpAuth;
import com.konai.kurong.faketee.employee.utils.EmpRole;
import com.konai.kurong.faketee.vacation.dto.VacTypeSaveRequestDto;
import com.konai.kurong.faketee.vacation.service.VacTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/corporation/{corId}/vac/type")
@RestController
public class VacTypeApiController {

    private final VacTypeService vacTypeService;

    @EmpAuth(role = EmpRole.GENERAL_MANAGER)
    @PostMapping(produces = "application/json; charset=utf-8")
    public ResponseEntity<?> newType(@RequestBody VacTypeSaveRequestDto requestDto,
                                     @RequestParam Long vacGroupId){

        return ResponseEntity.ok(vacTypeService.save(requestDto, vacGroupId));
    }

    @EmpAuth(role = EmpRole.GENERAL_MANAGER)
    @PostMapping("/delete")
    public void delete(@RequestParam Long vacTypeId){

        vacTypeService.delete(vacTypeId);
    }

    @EmpAuth(role = EmpRole.EMPLOYEE)
    @GetMapping("/by-cor")
    public ResponseEntity<?> listByCorId(@PathVariable(name = "corId") Long corId){

        return ResponseEntity.ok(vacTypeService.loadByCorId(corId));
    }

    @EmpAuth(role = EmpRole.GENERAL_MANAGER)
    @GetMapping("/by-vacgroup")
    public ResponseEntity<?> listByVacGroupId(@RequestParam Long vacGroupId){

        return ResponseEntity.ok(vacTypeService.loadByVacGroupId(vacGroupId));
    }

}
