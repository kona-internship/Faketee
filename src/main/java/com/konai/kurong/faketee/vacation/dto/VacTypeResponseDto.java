package com.konai.kurong.faketee.vacation.dto;

import com.konai.kurong.faketee.vacation.entity.VacGroup;
import com.konai.kurong.faketee.vacation.entity.VacType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacTypeResponseDto {

    private Long id;
    private String name;
    private Double sub;
    private String startTime;
    private String endTime;
    private VacGroupResponseDto vacGroupResponseDto;

    @QueryProjection
    public VacTypeResponseDto(VacType vacType){

        this.id = vacType.getId();
        this.name = vacType.getName();
        this.sub = vacType.getSub();
        this.startTime = vacType.getStartTime().toString();
        this.endTime = vacType.getEndTime().toString();
        this.vacGroupResponseDto = VacGroupResponseDto.convertToDto(vacType.getVacGroup());
    }

    public static VacTypeResponseDto convertToDto(VacType vacType){

        return VacTypeResponseDto.builder()
                .id(vacType.getId())
                .name(vacType.getName())
                .sub(vacType.getSub())
                .startTime(vacType.getStartTime().toString())
                .endTime(vacType.getEndTime().toString())
                .vacGroupResponseDto(VacGroupResponseDto.convertToDto(vacType.getVacGroup()))
                .build();
    }
}
