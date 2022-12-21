package com.konai.kurong.faketee.attend.dto;

import com.konai.kurong.faketee.attend.entity.Attend;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttendResponseDto {
    private String schStartTime;
    private String schEndTime;
    private String depName;
    //근무중등등
    private String state;
    private String atdStartTime;
    private String atdEndTime;
    //오전반차, 일반근무, 등등
    private String todaySch;

}
