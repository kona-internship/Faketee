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

    /**
     * 근무일정 템플릿 추가
     *
     * 46~47 dto 로 받은 근무유형 ID 로 근무유형을 find 하고 dto 에 근무유형을 저장한 후 dto 를 entity 로 save 한다.
     * 49~58 템플릿 entity 의 영속성 존재 범위 내에서 템플릿 entity 에 template department (들)을 save 한다.
     *
     * @param requestDto
     * @return
     */
    @Transactional
    public Long save(TemplateSaveRequestDto requestDto){

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

    /**
     * 템플릿 조회
     *
     * @param id : template ID
     * @return template entity
     */
    public Template findById(Long id){

        return templateRepository.findById(id).orElseThrow(() -> new TemplateNotFoundException());
    }

    /**
     * 템플릿 삭제
     *
     * 템플릿 entity 와 연관관계를 가지는 entity 들을 먼저 delete 한 후 템플릿 entity 를 delete 한다.
     *
     * @param id : template ID
     */
    @Transactional
    public void delete(Long id){

        templateDepartmentRepository.deleteByTmpId(id);
        templatePositionRepository.deleteByTmpId(id);
        templateRepository.deleteById(id);
    }

    /**
     * 회사의 템플릿(들)을 조회
     *
     * 회사 내에 존재하는 템플릿(들)을 찾는다.
     * 이때 찾는 템플릿 dto 에는 id, 템플릿 이름, 근무유형, 시작시간, 종료시간을 갖는다.
     * 템플릿에 포함된 조직들, 직무들은 다른 메서드를 통해 찾는다. loadDepartments(), loadTemplatePositions()
     *
     * @param corId : corporation ID
     * @return template response dto list
     */
    @Transactional
    public List<TemplateResponseDto> loadTemplates(Long corId){

        return templateRepository.findAllByCorId(corId);
    }

    /**
     * 특정 템플릿 조회
     *
     * 특정 템플릿을 찾는다.
     * 이때 찾는 템플릿 dto 에는 id, 템플릿 이름, 근무유형, 시작시간, 종료시간을 갖는다.
     * 템플릿에 포함된 조직들, 직무들은 다른 메서드를 통해 찾는다. loadDepartments(), loadTemplatePositions()
     *
     * @param tempId : template ID
     * @return template response dto
     */
    public TemplateResponseDto loadDetails(Long tempId){

        return new TemplateResponseDto(findById(tempId));
    }

    /**
     * ******* DEPRECATED ********
     * 특정 템플릿에 포함된 template departments (들)을 찾는다.
     *
     * department.js 의 showDeptList() 의 parameter 로 department response dto list 를 주기 위해 deprecated.
     *
     * @param tempId : template ID
     * @return template department response dto
     */
    public List<TemplateDepartmentResponseDto> loadTemplateDepartments(Long tempId) {

        return templateDepartmentRepository.findAllByTmpId(tempId);
    }

    /**
     * 특정 템플릿에 포함된 직무(들)을 찾는다.
     *
     * template position 객체가 들고 있는 position 정보를 활용하도록 한다.
     *
     * @param tempId : template ID
     * @return template position response dto
     */
    public List<TemplatePositionResponseDto> loadTemplatePositions(Long tempId) {

        return templatePositionRepository.findAllByTmpId(tempId);
    }

    /**
     * 특정 템플릿에 포함된 조직(들)을 찾는다.
     *
     * template department 객체가 들고 있는 department 객체를 활용해 department list 를 만들고 반환한다.
     *
     * @param tempId : template ID
     * @return department response dto list
     */
    public List<DepartmentResponseDto> loadDepartments(Long tempId){

        List<TemplateDepartmentResponseDto> templateDepartments = templateDepartmentRepository.findAllByTmpId(tempId);
        List<DepartmentResponseDto> departments = new ArrayList<>();
        for(TemplateDepartmentResponseDto templateDepartment : templateDepartments){
            departments.add(templateDepartment.getDepartment());
        }
        return departments;
    }
}
