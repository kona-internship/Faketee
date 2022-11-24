package com.konai.kurong.faketee.location.dto;

import com.konai.kurong.faketee.location.entity.Location;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Builder
public class LocationResponseDto {
    private String name;
    private String address;
    private Long radius;

    public static LocationResponseDto convertToDto(Location location) {
        return LocationResponseDto.builder()
                .name(location.getName())
                .address(location.getAddress())
                .radius(location.getRadius())
                .build();
    }

    public static List<LocationResponseDto> converToDtoList(List<Location> locationList) {
        Stream<Location> stream = locationList.stream();

        return stream.map(location -> convertToDto(location)).collect(Collectors.toList());
    }
}
