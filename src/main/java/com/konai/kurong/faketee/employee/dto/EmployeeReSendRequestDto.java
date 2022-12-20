package com.konai.kurong.faketee.employee.dto;

import com.konai.kurong.faketee.department.entity.Department;
import com.konai.kurong.faketee.department.service.DepartmentService;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class EmployeeReSendRequestDto {
    private static DepartmentService departmentService;

    private String email;
}
