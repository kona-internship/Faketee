package com.konai.kurong.faketee.vacation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacRequestFormDto {

    private Long vacTypeId;
    private List<String> dates;
    private List<Long> approvals;
    private String requestMessage;
}
