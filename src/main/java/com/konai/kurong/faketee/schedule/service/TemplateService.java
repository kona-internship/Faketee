package com.konai.kurong.faketee.schedule.service;

import com.konai.kurong.faketee.department.dto.DepartmentResponseDto;
import com.konai.kurong.faketee.department.service.DepartmentService;
import com.konai.kurong.faketee.position.service.PositionService;
import com.konai.kurong.faketee.schedule.dto.*;
import com.konai.kurong.faketee.schedule.entity.Template;
import com.konai.kurong.faketee.schedule.entity.TemplateDepartment;
import com.konai.kurong.faketee.schedule.entity.TemplatePosition;
import com.konai.kurong.faketee.schedule.repository.template.TemplateDepartmentRepository;
import com.konai.kurong.faketee.schedule.repository.template.TemplatePositionRepository;
import com.konai.kurong.faketee.schedule.repository.template.TemplateRepository;
import com.konai.kurong.faketee.utils.exception.custom.schedule.TemplateNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TemplateService {

    private final ScheduleTypeService scheduleTypeService;
    private final DepartmentService departmentService;
    private final PositionService positionService;
    private final TemplateRepository templateRepository;
    private final TemplateDepartmentRepository templateDepartmentRepository;
    private final TemplatePositionRepository templatePositionRepository;

    @Transactional
    public Long save(TemplateSaveRequestDto requestDto, Long corId){

        requestDto.setScheduleType(scheduleTypeService.findById(requestDto.getScheduleId()));
        Template template = templateRepository.save(requestDto.toEntity());

        List<TemplateDepartment> templateDepartments = new ArrayList<>();
        for(Long id : requestDto.getDepartmentsId()) {
            TemplateDepartment templateDepartment = templateDepartmentRepository.save(TemplateDepartment
                    .builder()
                    .template(template)
                    .department(departmentService.findById(id))
                    .build());
            templateDepartments.add(templateDepartment);
        }
        template.setTemplateDepartments(templateDepartments);

        List<TemplatePosition> templatePositions = new ArrayList<>();
        for(Long id : requestDto.getPositionsId()) {
            TemplatePosition templatePosition = templatePositionRepository.save(TemplatePosition
                    .builder()
                    .template(template)
                    .position(positionService.findById(id))
                    .build());
            templatePositions.add(templatePosition);
        }
        template.setTemplatePositions(templatePositions);

        return template.getId();
    }

    public Template findById(Long id){

        return templateRepository.findById(id).orElseThrow(() -> new TemplateNotFoundException());
    }

    @Transactional
    public void delete(Long id){

        templateDepartmentRepository.deleteByTmpId(id);
        templatePositionRepository.deleteByTmpId(id);
        templateRepository.deleteById(id);
    }

    @Transactional
    public List<TemplateResponseDto> loadTemplates(Long corId){

        return templateRepository.findAllByCorId(corId);
    }

    public TemplateResponseDto loadDetails(Long tempId){

        return new TemplateResponseDto(findById(tempId));
    }

    public List<TemplateDepartmentResponseDto> loadTemplateDepartments(Long tempId) {

        return templateDepartmentRepository.findAllByTmpId(tempId);
    }

    public List<TemplatePositionResponseDto> loadTemplatePositions(Long tempId) {

        return templatePositionRepository.findAllByTmpId(tempId);
    }

    public List<DepartmentResponseDto> loadDepartments(Long tempId){

        List<TemplateDepartmentResponseDto> templateDepartments = templateDepartmentRepository.findAllByTmpId(tempId);
        List<DepartmentResponseDto> departments = new ArrayList<>();
        for(TemplateDepartmentResponseDto templateDepartment : templateDepartments){
            departments.add(templateDepartment.getDepartment());
        }
        return departments;
    }
}
