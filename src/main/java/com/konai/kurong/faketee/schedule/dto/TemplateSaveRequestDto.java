package com.konai.kurong.faketee.schedule.dto;

import com.konai.kurong.faketee.department.entity.Department;
import com.konai.kurong.faketee.position.entity.Position;
import com.konai.kurong.faketee.schedule.entity.ScheduleType;
import com.konai.kurong.faketee.schedule.entity.Template;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateSaveRequestDto {

    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Department department;
    private ScheduleType scheduleType;
    private Position position;

    public Template toEntity(){

        return Template.builder()
                .name(name)
                .startTime(startTime)
                .endTime(endTime)
                .department(department)
                .scheduleType(scheduleType)
                .position(position)
                .build();
    }

}

