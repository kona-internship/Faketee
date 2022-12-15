package com.konai.kurong.faketee.vacation.dto;

import com.konai.kurong.faketee.corporation.dto.CorporationResponseDto;
import com.konai.kurong.faketee.corporation.entity.Corporation;
import com.konai.kurong.faketee.vacation.entity.VacGroup;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacGroupResponseDto {

    private Long id;
    private String name;
    private String approvalLevel;
    private CorporationResponseDto corporationResponseDto;

    @QueryProjection
    public VacGroupResponseDto(VacGroup vacGroup){

        this.id = vacGroup.getId();
        this.name = vacGroup.getName();
        this.approvalLevel = vacGroup.getApprovalLevel();
        this.corporationResponseDto = CorporationResponseDto.convertToDto(vacGroup.getCorporation());
    }

    public static VacGroupResponseDto convertToDto(VacGroup vacGroup){

        return VacGroupResponseDto.builder()
                .id(vacGroup.getId())
                .name(vacGroup.getName())
                .approvalLevel(vacGroup.getApprovalLevel())
                .corporationResponseDto(CorporationResponseDto.convertToDto(vacGroup.getCorporation()))
                .build();
    }

}
