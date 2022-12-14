package com.konai.kurong.faketee.attend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttendResponseDto {
    private String startTime;
    private String endTime;
    private String depName;

}
