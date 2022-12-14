package com.konai.kurong.faketee.employee.dto;

import com.konai.kurong.faketee.employee.entity.Employee;
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
public class EmployeeResponseDto {
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private String role;

    private Long corporationId;

    @NotNull
    private Long positionId;

    @NotNull
    private Long departmentId;

    private String joinDate;

    private String freeDate;

    private Long empNo;
    private String major;
    private String cert;
    private String info;
    private String val;

//    public EmployeeResponseDto(Employee employee) {
//        this.id = id;
//        this.name = employee.getName();
//        this.role = employee.getRole().toString();
//        this.positionId = employee.getPosition().getId();
//        this.departmentId = employee.getDepartment().getId();
//        this.joinDate = employee
//    }

    public static EmployeeResponseDto convertToDto(Employee employee){

        return EmployeeResponseDto.builder()
                .id(employee.getId())
                .name(employee.getName())
                .role(employee.getRole().toString())
                .corporationId(employee.getCorporation().getId())
                .positionId(employee.getPosition().getId())
                .departmentId(employee.getDepartment().getId())
                .val(employee.getVal())
                .joinDate(employee.getEmployeeInfo().getJoinDate().toString())
                .freeDate(employee.getEmployeeInfo().getFreeDate().toString())
                .empNo(employee.getEmployeeInfo().getEmpNo())
                .major(employee.getEmployeeInfo().getMajor())
                .cert(employee.getEmployeeInfo().getCert())
                .info(employee.getEmployeeInfo().getInfo())
                .build();
    }
}
