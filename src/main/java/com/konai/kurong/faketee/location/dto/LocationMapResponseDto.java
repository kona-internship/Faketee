package com.konai.kurong.faketee.location.dto;

import com.konai.kurong.faketee.location.entity.Location;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Builder
public class LocationMapResponseDto {
    private Long id;
    private String name;
    private BigDecimal lat;
    private BigDecimal lng;
    private Long radius;

    public static LocationMapResponseDto convertToDto(Location location) {
        return LocationMapResponseDto.builder()
                .id(location.getId())
                .name(location.getName())
                .lat(location.getLat())
                .lng(location.getLng())
                .radius(location.getRadius())
                .build();
    }

    public static List<LocationMapResponseDto> convertToDtoList(List<Location> locationList) {
        Stream<Location> stream = locationList.stream();

        return stream.map(location -> convertToDto(location)).collect(Collectors.toList());
    }
}
