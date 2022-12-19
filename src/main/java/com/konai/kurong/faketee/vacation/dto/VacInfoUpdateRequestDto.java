package com.konai.kurong.faketee.vacation.dto;

import com.konai.kurong.faketee.employee.entity.Employee;
import com.konai.kurong.faketee.vacation.entity.VacGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacInfoUpdateRequestDto {

    private Double add;
    private Long empId;
    private Long vacGroupId;
}
