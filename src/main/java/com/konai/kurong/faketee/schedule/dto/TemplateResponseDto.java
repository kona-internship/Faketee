package com.konai.kurong.faketee.schedule.dto;

import com.konai.kurong.faketee.department.entity.Department;
import com.konai.kurong.faketee.position.entity.Position;
import com.konai.kurong.faketee.schedule.entity.ScheduleType;
import com.konai.kurong.faketee.schedule.entity.Template;
import com.konai.kurong.faketee.schedule.entity.TemplateDepartment;
import com.konai.kurong.faketee.schedule.entity.TemplatePosition;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class TemplateResponseDto {

    private Long id;
    private String name;
    private LocalTime startTime;
    private LocalTime endTime;
    private List<TemplateDepartment> departments;
    private List<TemplatePosition> positions;
    private ScheduleType scheduleType;

    @QueryProjection
    public TemplateResponseDto(Template template) {

        this.id = template.getId();
        this.name = template.getName();
        this.startTime = template.getStartTime();
        this.endTime = template.getEndTime();
        this.departments = template.getTemplateDepartments();
        this.positions = template.getTemplatePositions();
        this.scheduleType = template.getScheduleType();
    }
}
