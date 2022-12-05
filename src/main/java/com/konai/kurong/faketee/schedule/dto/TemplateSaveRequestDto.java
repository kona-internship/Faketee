package com.konai.kurong.faketee.schedule.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.konai.kurong.faketee.department.entity.Department;
import com.konai.kurong.faketee.position.entity.Position;
import com.konai.kurong.faketee.schedule.entity.ScheduleType;
import com.konai.kurong.faketee.schedule.entity.Template;
import com.konai.kurong.faketee.schedule.entity.TemplateDepartment;
import com.konai.kurong.faketee.schedule.entity.TemplatePosition;
import com.konai.kurong.faketee.schedule.service.TemplateService;
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
@JsonAutoDetect
public class TemplateSaveRequestDto {

    private String name;
    private String startTime;
    private String endTime;
    private ScheduleType scheduleType;
    private Long scheduleId;
    private List<Long> departmentsId;
    private List<Long> positionsId;


    public Template toEntity(){

        return Template.builder()
                .name(name)
                .startTime(parseStringToLocalTime(startTime))
                .endTime(parseStringToLocalTime(endTime))
                .scheduleType(scheduleType)
                .build();
    }
    private LocalTime parseStringToLocalTime(String time) {

        return LocalTime.parse(time);
    }

    public void setScheduleType(ScheduleType scheduleType) {

        this.scheduleType = scheduleType;
    }


}

