package com.konai.kurong.faketee.location.dto;

import com.konai.kurong.faketee.corporation.entity.Corporation;
import com.konai.kurong.faketee.location.entity.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class LocationSaveRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotBlank
    private String radius;

    private BigDecimal lat;
    private BigDecimal lng;


    public Location toEntity(Corporation corporation){
        return Location.builder()
                .name(name)
                .address(address)
                .radius(Long.valueOf(radius))
                .lat(lat)
                .lng(lng)
                .corporation(corporation)
                .build();
    }
}
