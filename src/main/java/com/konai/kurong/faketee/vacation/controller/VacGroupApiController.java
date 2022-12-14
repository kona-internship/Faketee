package com.konai.kurong.faketee.vacation.controller;

import com.konai.kurong.faketee.vacation.dto.VacGroupSaveRequestDto;
import com.konai.kurong.faketee.vacation.service.VacGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/corporation/{corId}/vac")
@RestController
public class VacGroupApiController {

    private final VacGroupService vacGroupService;

    @PostMapping(value = "/group", produces = "application/json; charset=utf-8")
    public ResponseEntity<?> newGroup(@RequestBody VacGroupSaveRequestDto requestDto,
                                      @PathVariable(name = "corId") Long corId){

        return ResponseEntity.ok(vacGroupService.save(requestDto, corId));
    }

    @PostMapping("/delete")
    public void delete(@RequestParam Long id){

        vacGroupService.delete(id);
    }

    @GetMapping("/list")
    public ResponseEntity<?> loadVacationGroups(@PathVariable(name = "corId") Long corId){

        return ResponseEntity.ok(vacGroupService.loadVacGroups(corId));
    }

}
