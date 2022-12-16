package com.konai.kurong.faketee.employee.repository;

import com.konai.kurong.faketee.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, QuerydslEmpRepository {
    Optional<Employee> findByUserIdAndCorporationIdAndVal(Long userId, Long corId, String val);
    Optional<Employee> findByEmployeeInfoId(Long empInfoId);
    List<Employee> findByCorporationId(Long corId);

}
