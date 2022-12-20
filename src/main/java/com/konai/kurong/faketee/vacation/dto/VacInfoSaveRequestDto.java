package com.konai.kurong.faketee.vacation.dto;

import com.konai.kurong.faketee.employee.entity.Employee;
import com.konai.kurong.faketee.vacation.entity.VacGroup;
import com.konai.kurong.faketee.vacation.entity.VacInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacInfoSaveRequestDto {

    private Long id;
    private Double remaining;
    private Employee employee;
    private VacGroup vacGroup;

    public VacInfo toEntity(){

        return VacInfo.builder()
                .remaining(remaining)
                .used(0D)
                .employee(employee)
                .vacGroup(vacGroup)
                .build();
    }
}
