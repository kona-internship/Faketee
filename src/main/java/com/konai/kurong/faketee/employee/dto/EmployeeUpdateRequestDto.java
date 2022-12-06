package com.konai.kurong.faketee.employee.dto;

import com.konai.kurong.faketee.employee.entity.EmployeeInfo;
import com.konai.kurong.faketee.employee.utils.EmpRole;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@Data
public class EmployeeUpdateRequestDto {
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

//    public EmployeeInfo toEmployeeInfoEntity() {
//
//        return EmployeeInfo.builder()
//                .joinDate(joinDate)
//                .freeDate(freeDate)
//                .empNo(empNo)
//                .major(major)
//                .cert(cert)
//                .info(info)
//                .build();
//    }
}
