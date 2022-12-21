package com.konai.kurong.faketee.attend.dto;

import com.konai.kurong.faketee.attend.entity.Attend;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Builder
public class AttendResponseRecordDto {
    private Long id;
    private String date;
    private String startTime;
    private String endTime;

    public static AttendResponseRecordDto convertToDto(Attend attend) {
        return AttendResponseRecordDto.builder()
                .id(attend.getId())
                .date(attend.getDate().toString())
                .startTime(attend.getStartTime().toString())
                .endTime(String.valueOf(attend.getEndTime()))
                .build();
    }

    public static List<AttendResponseRecordDto> convertToDtoList(List<Attend> attendList) {
        Stream<Attend> stream = attendList.stream();
        return stream.map(Attend -> convertToDto(Attend)).collect(Collectors.toList());
    }
}
