package com.konai.kurong.faketee.schedule.dto.test;

import com.konai.kurong.faketee.schedule.entity.Template;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateDto {
    private Long id;
    private String name;
//    private List<TempDepDto> templateDepartments = new ArrayList<>();
//    private List<TempPosDto> templatePositions = new ArrayList<>();

    public static TemplateDto convertToDto(Template template){
        return TemplateDto.builder()
                .id(template.getId())
                .name(template.getName())
//                .templateDepartments(TempDepDto.convertToDtoList(template.getTemplateDepartments()))
//                .templatePositions(TempPosDto.convertToDtoList(template.getTemplatePositions()))
                .build();
    }

    public static List<TemplateDto> convertToDtoList(List<Template> templateList){
        Stream<Template> stream = templateList.stream();

        return stream.map(template -> convertToDto(template)).collect(Collectors.toList());
    }
}
