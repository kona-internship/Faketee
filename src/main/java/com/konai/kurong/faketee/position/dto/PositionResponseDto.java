package com.konai.kurong.faketee.position.dto;

import com.konai.kurong.faketee.position.entity.Position;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Builder
public class PositionResponseDto {

    private String name;

    public static PositionResponseDto convertToDto(Position position){
        return PositionResponseDto.builder()
                .name(position.getName())
                .build();
    }

    public static List<PositionResponseDto> convertToDtoList(List<Position> positionList) {
        Stream<Position> stream = positionList.stream();

        return stream.map((position) -> convertToDto(position)).collect(Collectors.toList());
    }
}
