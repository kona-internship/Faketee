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

    /**
     * front 에서 string 으로 입력받은 출근시간, 퇴근시간 정보를
     * entity 로 저장할 때 local date time 으로 변환해준다.
     *
     * @param time
     * @return
     */
    private LocalTime parseStringToLocalTime(String time) {

        return LocalTime.parse(time);
    }

    /**
     * front 에서 입력받은 근무유형의 id 값으로 근무유형 entity 를 find 하고
     * find 한 근무유형 entity 를 이 dto 의 근무유형에 저장해준다.
     *
     * @param scheduleType
     */
    public void setScheduleType(ScheduleType scheduleType) {

        this.scheduleType = scheduleType;
    }


}

