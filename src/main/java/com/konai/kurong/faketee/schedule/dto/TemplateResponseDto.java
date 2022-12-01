package com.konai.kurong.faketee.schedule.dto;

import com.konai.kurong.faketee.department.entity.Department;
import com.konai.kurong.faketee.position.entity.Position;
import com.konai.kurong.faketee.schedule.entity.ScheduleType;
import com.konai.kurong.faketee.schedule.entity.Template;
import com.querydsl.core.annotations.QueryProjection;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
public class TemplateResponseDto {

    private Long id;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Department department;
    private Position position;
    private ScheduleType scheduleType;

    @QueryProjection
    public TemplateResponseDto(Template template){

        this.id = template.getId();
        this.name = template.getName();
        this.startTime = template.getStartTime();
        this.endTime = template.getEndTime();
        this.department = template.getDepartment();
        this.position = template.getPosition();
        this.scheduleType = template.getScheduleType();
    }
}
