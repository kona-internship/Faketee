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
    private Long remain;
    private EmployeeResponseDto employeeResponseDto;
    private VacGroupResponseDto vacGroupResponseDto;

    @QueryProjection
    public VacInfoResponseDto(VacInfo vacInfo){

        this.id = vacInfo.getId();
        this.remain = vacInfo.getRemain();
        this.employeeResponseDto = EmployeeResponseDto.convertToDto(vacInfo.getEmployee());
        this.vacGroupResponseDto = VacGroupResponseDto.convertToDto(vacInfo.getVacGroup());
    }

    public static VacInfoResponseDto convertToDto(VacInfo vacInfo){

        return VacInfoResponseDto.builder()
                .id(vacInfo.getId())
                .remain(vacInfo.getRemain())
                .employeeResponseDto(EmployeeResponseDto.convertToDto(vacInfo.getEmployee()))
                .vacGroupResponseDto(VacGroupResponseDto.convertToDto(vacInfo.getVacGroup()))
                .build();
    }
}
