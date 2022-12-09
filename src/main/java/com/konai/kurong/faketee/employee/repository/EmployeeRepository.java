package com.konai.kurong.faketee.employee.repository;

import com.konai.kurong.faketee.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, QuerydslEmpRepository {
    List<Employee> findByUserIdAndCorporationId(Long userId, Long corId);
    Optional<Employee> findByEmployeeInfoId(Long empInfoId);
    List<Employee> findByCorporationId(Long corId);

}
