package com.konai.kurong.faketee.employee.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.konai.kurong.faketee.employee.entity.EmployeeInfo;
import com.konai.kurong.faketee.employee.utils.EmpRole;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeSaveRequestDto {
    @NotBlank
    private String name;

    @NotNull
    private String role;

    @NotNull
    private Long positionId;

    @NotNull
    private Long departmentId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date joinDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date freeDate;
    private Long empNo;
    private String major;
    private String cert;
    private String info;
    @NotBlank
    private String email;

//    public EmployeeInfo toEmployeeInfoEntity(String joinCode) {
//        return EmployeeInfo.builder()
//                .joinDate(new java.sql.Timestamp(joinDate.getTime()).toLocalDateTime())
//                .freeDate(new java.sql.Timestamp(freeDate.getTime()).toLocalDateTime())
//                .empNo(empNo)
//                .major(major)
//                .cert(cert)
//                .info(info)
//                .email(email)
//                .joinCode(joinCode)
//                .build();
//    }

//    public Employee toEmployeeEntity() {
//        return Employee.builder()
//                .name(name)
//                .EmpRole(role)
//                .major(major)
//                .cert(cert)
//                .info(info)
//                .email(email)
//                .build();
//    }
}
