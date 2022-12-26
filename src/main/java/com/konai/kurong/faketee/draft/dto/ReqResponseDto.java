package com.konai.kurong.faketee.draft.dto;

import com.konai.kurong.faketee.attend.entity.AttendRequest;
import com.konai.kurong.faketee.vacation.entity.VacRequest;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Builder
public class ReqResponseDto {
    private Long id;
    private LocalDate date;
    private String vacType;


    public static ReqResponseDto convertVacToDto(VacRequest vacDateRequest){
        return ReqResponseDto.builder()
                .id(vacDateRequest.getId())
                .date(vacDateRequest.getDate())
                .vacType(vacDateRequest.getVacType().getName())
                .build();
    }

    public static List<ReqResponseDto> convertToVacDtoList(List<VacRequest> vacDateRequestList) {
        Stream<VacRequest> stream = vacDateRequestList.stream();
        return stream.map((request) -> convertVacToDto(request)).collect(Collectors.toList());
    }

    public static ReqResponseDto convertToAtdDto(AttendRequest attendRequest){
        return ReqResponseDto.builder()
                .id(attendRequest.getId())
                .date(attendRequest.getAtdReqDate())
                .build();
    }

    public static List<ReqResponseDto> convertToAtdDtoList(List<AttendRequest> attendRequestList) {
        Stream<AttendRequest> stream = attendRequestList.stream();
        return stream.map((request) -> convertToAtdDto(request)).collect(Collectors.toList());
    }
}
