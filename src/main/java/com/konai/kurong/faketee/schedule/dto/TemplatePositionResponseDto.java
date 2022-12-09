package com.konai.kurong.faketee.schedule.dto;

import com.konai.kurong.faketee.position.dto.PositionResponseDto;
import com.konai.kurong.faketee.position.entity.Position;
import com.konai.kurong.faketee.schedule.entity.Template;
import com.konai.kurong.faketee.schedule.entity.TemplatePosition;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplatePositionResponseDto {

    private Long id;
    private TemplateResponseDto template;
    private PositionResponseDto position;

    @QueryProjection
    public TemplatePositionResponseDto(TemplatePosition templatePosition){

        this.id = templatePosition.getId();
        this.template = new TemplateResponseDto(templatePosition.getTemplate());
        this.position = PositionResponseDto.convertToDto(templatePosition.getPosition());
    }

    public static TemplatePositionResponseDto convertToDto(TemplatePosition templatePosition){

        return TemplatePositionResponseDto.builder()
                .id(templatePosition.getId())
                .template(TemplateResponseDto.convertToDto(templatePosition.getTemplate()))
                .position(PositionResponseDto.convertToDto(templatePosition.getPosition()))
                .build();
    }
}
