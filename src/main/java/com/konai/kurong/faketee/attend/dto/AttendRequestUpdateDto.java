package com.konai.kurong.faketee.attend.dto;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AttendRequestUpdateDto {

    private String startTime;
    private String endTime;
    private Long empId;
    private Long draftId;
}
