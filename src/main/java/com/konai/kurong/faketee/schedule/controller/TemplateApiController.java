package com.konai.kurong.faketee.schedule.controller;

import com.konai.kurong.faketee.department.dto.DepartmentResponseDto;
import com.konai.kurong.faketee.schedule.dto.TemplateDepartmentResponseDto;
import com.konai.kurong.faketee.schedule.dto.TemplatePositionResponseDto;
import com.konai.kurong.faketee.schedule.dto.TemplateResponseDto;
import com.konai.kurong.faketee.schedule.dto.TemplateSaveRequestDto;
import com.konai.kurong.faketee.schedule.service.TemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/corporation/{corId}/template")
@RestController
public class TemplateApiController {

    private final TemplateService templateService;

    @PostMapping(value = "/new", produces = "application/json; charset=utf-8")
    public ResponseEntity<?> newTemplate(@RequestBody TemplateSaveRequestDto requestDto,
                                         @PathVariable(name = "corId") Long corId){

        return ResponseEntity.ok(templateService.save(requestDto, corId));
    }

    @PostMapping("/delete")
    public void delete(@RequestParam Long id){

        templateService.delete(id);
    }

    @GetMapping("/list")
    public ResponseEntity<?> loadTemplates(@PathVariable(name = "corId") Long corId){

        List<TemplateResponseDto> responseDtos = templateService.loadTemplates(corId);
        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/departments")
    public ResponseEntity<?> loadDepartments(@RequestParam Long tempId){

        List<TemplateDepartmentResponseDto> responseDtos = templateService.loadTemplateDepartments(tempId);
        List<DepartmentResponseDto> departments = templateService.loadDepartments(tempId);
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/positions")
    public ResponseEntity<?> loadPositions(@RequestParam Long tempId){

        List<TemplatePositionResponseDto> responseDtos = templateService.loadTemplatePositions(tempId);
        return ResponseEntity.ok(responseDtos);
    }
}
