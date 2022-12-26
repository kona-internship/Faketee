package com.konai.kurong.faketee.vacation.service;


import com.konai.kurong.faketee.department.service.DepartmentService;
import com.konai.kurong.faketee.employee.dto.EmployeeResponseDto;
import com.konai.kurong.faketee.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class VacRequestService {

    private final VacTypeService vacTypeService;
    private final EmployeeService employeeService;

    public List<EmployeeResponseDto> findApprovalLine(Long corId, Long empID){

        return employeeService.findApprovalLine(corId, employeeService.findById(empID).getDepartmentId());
    }

}
