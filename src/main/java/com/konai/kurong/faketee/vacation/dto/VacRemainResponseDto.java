package com.konai.kurong.faketee.vacation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacRemainResponseDto {

    private String vacGroup;
    private Double remain;
    private Double sub;
}
