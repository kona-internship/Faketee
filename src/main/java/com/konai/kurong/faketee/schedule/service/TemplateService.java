package com.konai.kurong.faketee.schedule.service;

import com.konai.kurong.faketee.schedule.dto.TemplateResponseDto;
import com.konai.kurong.faketee.schedule.dto.TemplateSaveRequestDto;
import com.konai.kurong.faketee.schedule.entity.ScheduleType;
import com.konai.kurong.faketee.schedule.entity.Template;
import com.konai.kurong.faketee.schedule.repository.ScheduleTypeRepository;
import com.konai.kurong.faketee.schedule.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TemplateService {

    private final TemplateRepository templateRepository;
    private final ScheduleTypeRepository scheduleTypeRepository;

    @Transactional
    public Long save(TemplateSaveRequestDto requestDto, Long corId){

        requestDto.setScheduleType(translateScheduleType(requestDto.getScheduleName(), corId));
        Template template = templateRepository.save(requestDto.toEntity());
        return template.getId();
    }

    @Transactional
    public void delete(Long id){

        templateRepository.deleteById(id);
    }

    public List<TemplateResponseDto> getTemplates(Long corId){

        return templateRepository.findAllByCorId(corId);
    }

    public ScheduleType translateScheduleType(String scheduleName, Long corId) {

        return scheduleTypeRepository.findByNameAndCorId(scheduleName, corId);
    }
}
