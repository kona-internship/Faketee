package com.konai.kurong.faketee.vacation.controller;

import com.konai.kurong.faketee.vacation.dto.VacTypeSaveRequestDto;
import com.konai.kurong.faketee.vacation.service.VacTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/corporation/{corId}/vac")
@RestController
public class VacTypeApiController {

    private final VacTypeService vacTypeService;

    @PostMapping(value = "/type", produces = "application/json; charset=utf-8")
    public ResponseEntity<?> newType(@RequestBody VacTypeSaveRequestDto requestDto,
                                     @RequestParam Long vacGroupId){

        return ResponseEntity.ok(vacTypeService.save(requestDto, vacGroupId));
    }

    @PostMapping("/delete")
    public void delete(@RequestParam Long vacTypeId){

        vacTypeService.delete(vacTypeId);
    }

    @GetMapping("/list")
    public ResponseEntity<?> listByCorId(@PathVariable(name = "corId") Long corId){

        return ResponseEntity.ok(vacTypeService.loadVacTypesByCorId(corId));
    }

    @GetMapping("/list")
    public ResponseEntity<?> listByVacGroupId(@RequestParam Long vacGroupId){

        return ResponseEntity.ok(vacTypeService.loadVacTypesByVacGroupId(vacGroupId));
    }

}
