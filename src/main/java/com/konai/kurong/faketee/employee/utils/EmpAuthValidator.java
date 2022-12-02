package com.konai.kurong.faketee.employee.utils;

import com.konai.kurong.faketee.employee.entity.Employee;
import com.konai.kurong.faketee.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class EmpAuthValidator {
    private final EmployeeRepository employeeRepository;

    public void validateEmployee(EmpRole role, Long empId){
        Employee employee = employeeRepository.findById(empId).orElseThrow(()->new IllegalArgumentException());

        // 설정해놓은 권한보다 요청한 직원의 권한이 더 낮을 때
        if(role.compareTo(employee.getRole())<0){
            throw new RuntimeException();
        }

        if(employee.getRole().equals(EmpRole.GENERAL_MANAGER)){
            
        }
    }
}
