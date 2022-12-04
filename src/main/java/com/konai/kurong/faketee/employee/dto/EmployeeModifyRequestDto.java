package com.konai.kurong.faketee.employee.dto;

import com.konai.kurong.faketee.employee.entity.EmployeeInfo;
import com.konai.kurong.faketee.employee.utils.EmpRole;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class EmployeeModifyRequestDto {
    @NotBlank
    private String name;

    @NotNull
    private EmpRole role;

    @NotNull
    private Long positionId;

    @NotNull
    private Long departmentId;

    private LocalDateTime joinDate;
    private LocalDateTime freeDate;
    private Long empNo;
    private String major;
    private String cert;
    private String info;

    public EmployeeInfo toEmployeeInfoEntity() {

        return EmployeeInfo.builder()
                .joinDate(joinDate)
                .freeDate(freeDate)
                .empNo(empNo)
                .major(major)
                .cert(cert)
                .info(info)
                .build();
    }
}
