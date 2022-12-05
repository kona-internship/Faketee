package com.konai.kurong.faketee.schedule.service;

import com.konai.kurong.faketee.department.repository.DepartmentRepository;
import com.konai.kurong.faketee.department.service.DepartmentService;
import com.konai.kurong.faketee.position.repository.PositionRepository;
import com.konai.kurong.faketee.position.service.PositionService;
import com.konai.kurong.faketee.schedule.dto.TemplateResponseDto;
import com.konai.kurong.faketee.schedule.dto.TemplateSaveRequestDto;
import com.konai.kurong.faketee.schedule.entity.ScheduleType;
import com.konai.kurong.faketee.schedule.entity.Template;
import com.konai.kurong.faketee.schedule.entity.TemplateDepartment;
import com.konai.kurong.faketee.schedule.entity.TemplatePosition;
import com.konai.kurong.faketee.schedule.repository.schedule.ScheduleTypeRepository;
import com.konai.kurong.faketee.schedule.repository.template.TemplateDepartmentRepository;
import com.konai.kurong.faketee.schedule.repository.template.TemplatePositionRepository;
import com.konai.kurong.faketee.schedule.repository.template.TemplateRepository;
import com.konai.kurong.faketee.utils.exception.custom.Schedule.TemplateDepartmentNotFoundException;
import com.konai.kurong.faketee.utils.exception.custom.Schedule.TemplatePositionNotFoundException;
import com.konai.kurong.faketee.utils.exception.custom.department.DepartmentNotFoundException;
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

    private final TemplateRepository templateRepository;
    private final ScheduleTypeService scheduleTypeService;
    private final DepartmentService departmentService;
    private final PositionService positionService;
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

    @Transactional
    public void delete(Long id){

        templateRepository.deleteById(id);
    }

    public List<TemplateResponseDto> getTemplates(Long corId){

        return templateRepository.findAllByCorId(corId);
    }
}
