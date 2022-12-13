package com.konai.kurong.faketee.vacation.dto;


import com.konai.kurong.faketee.corporation.dto.CorporationResponseDto;
import com.konai.kurong.faketee.corporation.entity.Corporation;
import com.konai.kurong.faketee.vacation.entity.VacGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacGroupSaveRequestDto {

    private Long id;
    private String name;
    private Corporation corporation;

    public VacGroup toEntity(){

        return VacGroup.builder()
                .name(name)
                .corporation(corporation)
                .build();
    }

    public void setCorporation(Corporation corporation){

        this.corporation = corporation;
    }
}
