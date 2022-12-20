package com.konai.kurong.faketee.draft.dto;

import com.konai.kurong.faketee.vacation.entity.VacRequest;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Builder
public class ReqDateResponseDto {
    private Long id;
    private LocalDateTime date;

    public static ReqDateResponseDto convertToDto(VacRequest vacRequest){
        return ReqDateResponseDto.builder()
                .id(vacRequest.getId())
                .date(vacRequest.getDate())
                .build();
    }

    public static List<ReqDateResponseDto> convertToDtoList(List<VacRequest> vacDateRequestList) {
        Stream<VacRequest> stream = vacDateRequestList.stream();
        return stream.map((request) -> convertToDto(request)).collect(Collectors.toList());
    }

//    public static List<ReqDateResponseDto> convertToDtoList(List<Atd> vacDateRequestList) {
//        Stream<VacDateRequest> stream = vacDateRequestList.stream();
//        return stream.map((request) -> convertToDto(request)).collect(Collectors.toList());
//    }
}
