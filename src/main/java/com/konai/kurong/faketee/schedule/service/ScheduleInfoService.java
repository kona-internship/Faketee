package com.konai.kurong.faketee.schedule.service;

import com.konai.kurong.faketee.schedule.dto.test.TempDepDto;
import com.konai.kurong.faketee.schedule.dto.test.TemplateDto;
import com.konai.kurong.faketee.schedule.entity.Template;
import com.konai.kurong.faketee.schedule.repository.template.TemplateRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleInfoService {
    private final TemplateService templateService;
    private final TemplateRepository templateRepository;

    @Transactional
    public Result<?> getAllRelationWithTemp(Long corId) {

        List<Template> templateList = templateRepository.findTemplatesByScheduleTypeCorporationIdOrderById(corId);
        List<TemplateDto> templateDtoList = TemplateDto.convertToDtoList(templateList);

        Map<Long, List<TempDepDto>> depsWithTempId = new HashMap<Long, List<TempDepDto>>();
        for(int i = 0; i < templateDtoList.size(); i++){
            Long tempId = templateDtoList.get(i).getId();
            List<TempDepDto> tempDepDtos = TempDepDto.convertToDtoList(templateList.get(i).getTemplateDepartments());

            depsWithTempId.put(tempId, tempDepDtos);
        }

//        log.info(templateList.get(0).getTemplateDepartments().toString());
        //회사에 해당되는 템플릿 리스트
//        List<TemplateResponseDto> tempResponseDtos = templateService.loadTemplates(corId);
//
//        Map<Long, List<TemplateDepartmentResponseDto>> depsWithTempId= new HashMap<Long, List<TemplateDepartmentResponseDto>>();
//        //각 템플릿에 해당되는 조직들 리스트
//        for(int i = 0; i < tempResponseDtos.size(); i++){
//            Long tempId = tempResponseDtos.get(i).getId();
//            List<TemplateDepartmentResponseDto> tempDeptResponseDtos = templateService.loadTemplateDepartments(tempId);
//
//            depsWithTempId.put(tempId, tempDeptResponseDtos);
//        }
//
//        return new Result<>(tempResponseDtos, depsWithTempId);
        return new Result<>(templateDtoList, depsWithTempId);
    }

    @Getter
    @Setter
    private class Result<T> {
        private T temp;
        private T dep;

        private T pos;
        private T emp;
        public Result(T temp) {
            this.temp = temp;

        }
        public Result(T temp, T dep) {
            this.temp = temp;
            this.dep = dep;
        }

        public Result(T temp, T dep, T pos) {
            this.temp = temp;
            this.dep = dep;
            this.pos = pos;
        }
        public Result(T temp, T dep, T pos, T emp) {
            this.temp = temp;
            this.dep = dep;
            this.pos = pos;
            this.emp = emp;
        }
    }
}
