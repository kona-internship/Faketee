package com.konai.kurong.faketee.employee.repository;

import com.konai.kurong.faketee.employee.entity.EmployeeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeInfoRepository extends JpaRepository<EmployeeInfo, Long> {
}
