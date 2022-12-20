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
@RequestMapping("/api/corporation/{corId}/temp")
@RestController
public class TemplateApiController {

    private final TemplateService templateService;

    /**
     * 근무일정 템플릿 등록
     *
     * @param requestDto : save request dto
     * @return
     */
    @PostMapping(value = "/new", produces = "application/json; charset=utf-8")
    public ResponseEntity<?> newTemplate(@RequestBody TemplateSaveRequestDto requestDto){

        return ResponseEntity.ok(templateService.save(requestDto));
    }

    /**
     * 근무일정 템플릿 삭제
     *
     * @param id : template ID
     */
    @PostMapping("/delete")
    public void delete(@RequestParam Long id){

        templateService.delete(id);
    }

    /**
     * 근무일정 템플릿(들) 조회
     *
     * @param corId : corporation ID
     * @return corporation 내의 템플릿을 List로 return
     */
    @GetMapping("/list")
    public ResponseEntity<?> loadTemplates(@PathVariable(name = "corId") Long corId){

//        List<TemplateResponseDto> responseDtos = templateService.loadTemplates(corId);
        return ResponseEntity.ok(templateService.loadTemplates(corId));
    }

    /**
     * 특정 템플릿 내의 조직들 조회
     *
     * @param tempId : template ID
     * @return template 내의 조직들을 List로 return
     */
    @GetMapping("/departments")
    public ResponseEntity<?> loadDepartments(@RequestParam Long tempId){

//        List<TemplateDepartmentResponseDto> responseDtos = templateService.loadTemplateDepartments(tempId);       deprecated
        List<DepartmentResponseDto> departments = templateService.loadDepartments(tempId);
        return ResponseEntity.ok(departments);
    }

    /**
     * 특정 템플릿 내의 직무들 조회
     *
     * @param tempId : template ID
     * @return template 내의 직무들을 List로 return
     */
    @GetMapping("/positions")
    public ResponseEntity<?> loadPositions(@RequestParam Long tempId){

        List<TemplatePositionResponseDto> responseDtos = templateService.loadTemplatePositions(tempId);
        return ResponseEntity.ok(responseDtos);
    }
}
