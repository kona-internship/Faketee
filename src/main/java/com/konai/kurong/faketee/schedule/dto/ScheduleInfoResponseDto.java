package com.konai.kurong.faketee.schedule.dto;

import com.konai.kurong.faketee.schedule.entity.ScheduleInfo;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Builder
public class ScheduleInfoResponseDto {
    private Long id;
    private String empName;
    private String startTime;
    private String endTime;
    private String state;

    public static String schState(ScheduleInfo scheduleInfo){
        String str = scheduleInfo.getState();
        String[] afterSplit = str.split("_");

        return afterSplit[1];
    }

    public static ScheduleInfoResponseDto convertToDto(ScheduleInfo scheduleInfo){
        return ScheduleInfoResponseDto.builder()
                .id(scheduleInfo.getId())
                .empName(scheduleInfo.getEmployee().getName())
                .state(schState(scheduleInfo))
                .startTime(scheduleInfo.getStartTime().toString())
                .endTime(scheduleInfo.getEndTime().toString())
                .build();
    }

    public static List<ScheduleInfoResponseDto> convertToDtoList(List<ScheduleInfo> scheduleInfoList){
        Stream<ScheduleInfo> stream = scheduleInfoList.stream();

        return stream.map((scheduleInfo) -> convertToDto(scheduleInfo)).collect(Collectors.toList());
    }
}
