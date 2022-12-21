package com.konai.kurong.faketee.corporation.dto;

import com.konai.kurong.faketee.corporation.entity.Corporation;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CorporationResponseDto {

    private Long id;
    private String name;

    public static CorporationResponseDto convertToDto(Corporation corporation) {
        return CorporationResponseDto.builder()
                .id(corporation.getId())
                .name(corporation.getName())
                .build();
    }
}
