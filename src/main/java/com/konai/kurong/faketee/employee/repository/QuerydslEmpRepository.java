package com.konai.kurong.faketee.employee.repository;

import com.konai.kurong.faketee.employee.entity.Employee;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuerydslEmpRepository {
    List<Employee> getEmployeeByUserAndCorAndVal(Long usrId, Long corId, String val);

    List<Employee> findByDepId(Long depId);
    List<Employee> findByUserId(Long userId);
    List<Employee> getEmployeeByDepAndPos(List<Long> deps, List<Long> pos);
    List<Employee> findApprovalLine(@Param("corId") Long corId, @Param("depId") Long depId);
}
