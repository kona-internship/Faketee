package com.konai.kurong.faketee.vacation.dto;

import com.konai.kurong.faketee.employee.dto.EmployeeResponseDto;
import com.konai.kurong.faketee.vacation.entity.VacInfo;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacInfoResponseDto {

    private Long id;
    private Double remaining;
    private Double used;
    private Double total;
    private EmployeeResponseDto employeeResponseDto;
    private VacGroupResponseDto vacGroupResponseDto;

    public VacInfoResponseDto(VacInfo vacInfo){

        this.id = vacInfo.getId();
        this.remaining = vacInfo.getRemaining();
        this.used = vacInfo.getUsed();
        this.total = vacInfo.getRemaining() + vacInfo.getUsed();
        this.employeeResponseDto = EmployeeResponseDto.convertToDto(vacInfo.getEmployee());
        this.vacGroupResponseDto = VacGroupResponseDto.convertToDto(vacInfo.getVacGroup());
    }

    public static VacInfoResponseDto convertToDto(VacInfo vacInfo){

        return VacInfoResponseDto.builder()
                .id(vacInfo.getId())
                .remaining(vacInfo.getRemaining())
                .used(vacInfo.getUsed())
                .total(vacInfo.getRemaining() + vacInfo.getUsed())
                .employeeResponseDto(EmployeeResponseDto.convertToDto(vacInfo.getEmployee()))
                .vacGroupResponseDto(VacGroupResponseDto.convertToDto(vacInfo.getVacGroup()))
                .build();
    }
}
