package com.konai.kurong.faketee.schedule.dto;

import com.konai.kurong.faketee.schedule.entity.ScheduleType;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Builder
public class ScheduleTypeResponseDto {
    private Long id;
    private String name;

    public static ScheduleTypeResponseDto convertToDto(ScheduleType scheduleType) {
        return ScheduleTypeResponseDto.builder()
                .id(scheduleType.getId())
                .name(scheduleType.getName())
                .build();
    }

    public static List<ScheduleTypeResponseDto> convertToDtoList(List<ScheduleType> scheduleTypeList) {
        Stream<ScheduleType> stream = scheduleTypeList.stream();

        return stream.map((scheduleType) -> convertToDto(scheduleType)).collect(Collectors.toList());
    }
}
