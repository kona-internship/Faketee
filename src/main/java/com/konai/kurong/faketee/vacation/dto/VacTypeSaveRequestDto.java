package com.konai.kurong.faketee.vacation.dto;

import com.konai.kurong.faketee.vacation.entity.VacGroup;
import com.konai.kurong.faketee.vacation.entity.VacType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacTypeSaveRequestDto {

    private Long id;
    private String name;
    private Double sub;
    private String startTime;
    private String endTime;
    private VacGroup vacGroup;

    public VacType toEntity(){

        return VacType.builder()
                .name(name)
                .sub(sub)
                .startTime(parseStringToLocalTime(startTime))
                .endTime(parseStringToLocalTime(endTime))
                .vacGroup(vacGroup)
                .build();
    }

    private LocalTime parseStringToLocalTime(String time){

        return LocalTime.parse(time);
    }

    public void setVacGroup(VacGroup vacGroup){

        this.vacGroup = vacGroup;
    }
}
