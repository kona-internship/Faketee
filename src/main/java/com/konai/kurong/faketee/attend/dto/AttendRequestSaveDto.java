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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String startTime;
    private String endTime;
    private Long empId;
    private Long draftId;
}
