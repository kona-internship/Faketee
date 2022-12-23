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

    String vacGroup;
    Double remain;
    Double sub;
}
