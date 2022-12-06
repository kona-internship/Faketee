package com.konai.kurong.faketee.schedule.dto;

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
    private Template template;
    private Position position;

    @QueryProjection
    public TemplatePositionResponseDto(TemplatePosition templatePosition){

        this.id = templatePosition.getId();
        this.template = templatePosition.getTemplate();
        this.position = templatePosition.getPosition();
    }
}
