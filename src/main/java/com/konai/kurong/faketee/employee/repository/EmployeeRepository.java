package com.konai.kurong.faketee.employee.repository;

import com.konai.kurong.faketee.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface EmployeeRepository extends JpaRepository<Employee, Long>, QuerydslEmpRepository {
    List<Employee> findByCorporationId(Long corId);
}
