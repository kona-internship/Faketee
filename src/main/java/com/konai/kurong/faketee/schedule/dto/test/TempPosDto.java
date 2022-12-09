package com.konai.kurong.faketee.schedule.dto.test;

import com.konai.kurong.faketee.position.dto.PositionResponseDto;
import com.konai.kurong.faketee.schedule.entity.TemplatePosition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TempPosDto {
    private Long id;
    private PositionResponseDto position;

    public static TempPosDto convertToDto (TemplatePosition templatePosition){
        return TempPosDto.builder()
                .id(templatePosition.getId())
                .position(PositionResponseDto.convertToDto(templatePosition.getPosition()))
                .build();
    }
    public static List<TempPosDto> convertToDtoList(List<TemplatePosition> templatePositionList){
        Stream<TemplatePosition> stream = templatePositionList.stream();

        return stream.map(templatePosition -> convertToDto(templatePosition)).collect(Collectors.toList());
    }
}
