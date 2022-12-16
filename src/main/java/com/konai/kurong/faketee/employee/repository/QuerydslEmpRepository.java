package com.konai.kurong.faketee.employee.repository;

import com.konai.kurong.faketee.employee.entity.Employee;

import java.util.List;

public interface QuerydslEmpRepository {
    List<Employee> getEmployeeByUserAndCorAndVal(Long usrId, Long corId, String val);
    List<Employee> findByDepId(Long depId);
    List<Employee> findByUserId(Long userId);
}
