package com.konai.kurong.faketee.employee.dto;

import com.konai.kurong.faketee.employee.utils.EmpRole;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class EmployeeResponseDto {
    private String name;
    private EmpRole role;
    private Long positionId;
    private Long departmentId;
    private LocalDateTime joinDate;
    private LocalDateTime freeDate;
    private Long empNo;
    private String major;
    private String cert;
    private String info;
}
