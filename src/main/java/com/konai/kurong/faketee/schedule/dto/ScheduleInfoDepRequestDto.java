package com.konai.kurong.faketee.schedule.dto;

import lombok.Data;

import java.util.List;

@Data
public class ScheduleInfoDepRequestDto {
    private Long tempId;
    private List<Long> checkedDep;

}
