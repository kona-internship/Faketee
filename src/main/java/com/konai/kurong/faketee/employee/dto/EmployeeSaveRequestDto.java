package com.konai.kurong.faketee.employee.dto;

import com.konai.kurong.faketee.department.entity.Department;
import com.konai.kurong.faketee.department.service.DepartmentService;
import com.konai.kurong.faketee.employee.utils.AuthIdsDto;
import com.konai.kurong.faketee.employee.utils.EmpAuthCheckList;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeSaveRequestDto implements AuthIdsDto {
    private static DepartmentService departmentService;

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

    @Override
    public EmpAuthCheckList getEmpAuthCheckList() {
        Department department = departmentService.findDepartmentById(departmentId);
        List<Department> depList = departmentService.getSubDepList(department);
        List<Long> depIdList = new ArrayList<>();
        for(Department dep : depList) {
            depIdList.add(dep.getId());
        }
        return EmpAuthCheckList.builder()
                .idList(depIdList)
                .build();
    }

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
