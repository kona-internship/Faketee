package com.konai.kurong.faketee.schedule.service;

import com.konai.kurong.faketee.schedule.dto.TemplateResponseDto;
import com.konai.kurong.faketee.schedule.dto.TemplateSaveRequestDto;
import com.konai.kurong.faketee.schedule.entity.Template;
import com.konai.kurong.faketee.schedule.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TemplateService {

    private final TemplateRepository templateRepository;

    @Transactional
    public Long save(TemplateSaveRequestDto requestDto){

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
}
