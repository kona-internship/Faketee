package com.konai.kurong.faketee.schedule.controller;

import com.konai.kurong.faketee.schedule.dto.TemplateSaveRequestDto;
import com.konai.kurong.faketee.schedule.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/corporation/{corId}/template")
@RestController
public class TemplateApiController {

    private final TemplateService templateService;

    @PostMapping("/new")
    public ResponseEntity<?> newTemplate(@RequestBody TemplateSaveRequestDto requestDto){

        return ResponseEntity.ok(templateService.save(requestDto));
    }

    @PostMapping("/delete")
    public void delete(@RequestParam Long id){

        templateService.delete(id);
    }

}
