package com.konai.kurong.faketee.schedule.dto;

import com.konai.kurong.faketee.department.entity.Department;
import com.konai.kurong.faketee.position.entity.Position;
import com.konai.kurong.faketee.schedule.entity.ScheduleType;
import com.konai.kurong.faketee.schedule.entity.Template;
import com.konai.kurong.faketee.schedule.entity.TemplateDepartment;
import com.konai.kurong.faketee.schedule.entity.TemplatePosition;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateResponseDto {

    private Long id;
    private String name;
    private String startTime;
    private String endTime;
    private ScheduleTypeResponseDto scheduleType;

    @QueryProjection
    public TemplateResponseDto(Template template) {

        this.id = template.getId();
        this.name = template.getName();
        this.startTime = template.getStartTime().toString();
        this.endTime = template.getEndTime().toString();
        this.scheduleType = ScheduleTypeResponseDto.convertToDto(template.getScheduleType());
    }

    public static TemplateResponseDto convertToDto(Template template) {

        return TemplateResponseDto.builder()
                .id(template.getId())
                .name(template.getName())
                .startTime(template.getStartTime().toString())
                .endTime(template.getEndTime().toString())
                .scheduleType(ScheduleTypeResponseDto.convertToDto(template.getScheduleType()))
                .build();
    }
}
