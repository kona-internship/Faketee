package com.konai.kurong.faketee.attend.dto;

import com.konai.kurong.faketee.attend.entity.AttendRequest;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AttendRequestSaveDto {
    private String requestMessage;
    private Long apvEmpFinId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date atdReqDate;
    private String startTime;
    private String endTime;
    private Long reqEmpId;
}
