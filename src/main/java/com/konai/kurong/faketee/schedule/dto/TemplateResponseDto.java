package com.konai.kurong.faketee.schedule.dto;

import com.konai.kurong.faketee.schedule.entity.Template;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Builder
public class TemplateResponseDto {
    private Long id;
    private String name;

    public static TemplateResponseDto convertToDto(Template template) {
        return TemplateResponseDto.builder()
                .id(template.getId())
                .name(template.getName())
                .build();
    }

    public static List<TemplateResponseDto> convertToDtoList(List<Template> templateList) {
        Stream<Template> stream = templateList.stream();

        return stream.map((Template) -> convertToDto(Template)).collect(Collectors.toList());
    }

}
