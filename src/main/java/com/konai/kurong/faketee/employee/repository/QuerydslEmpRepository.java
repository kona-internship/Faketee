package com.konai.kurong.faketee.employee.repository;

import com.konai.kurong.faketee.employee.dto.EmployeeResponseDto;
import com.konai.kurong.faketee.employee.entity.Employee;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuerydslEmpRepository {
    List<Employee> getEmployeeByUserAndCorAndVal(Long usrId, Long corId, String val);

    List<Employee> findByDepId(Long depId);
    List<Employee> findByUserId(Long userId);
    List<Employee> getEmployeeByDepAndPos(List<Long> deps, List<Long> pos);
    Employee findAdminApproval(@Param("corId") Long corId);
    List<Employee> findApprovalLine(@Param("corId") Long corId, @Param("depId") Long depId);
}
