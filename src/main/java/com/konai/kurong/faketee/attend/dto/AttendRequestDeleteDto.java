package com.konai.kurong.faketee.attend.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AttendRequestDeleteDto {
    private String requestMessage;
    private Long apvEmpFinId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date atdReqDate;
    private Long reqEmpId;
}
