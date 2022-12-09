package com.konai.kurong.faketee.employee.repository;

import com.konai.kurong.faketee.employee.entity.EmployeeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeInfoRepository extends JpaRepository<EmployeeInfo, Long> {
    Optional<EmployeeInfo> findByJoinCode(String joinCodeId);
}
