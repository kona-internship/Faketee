package com.konai.kurong.faketee.department.repository;

import com.konai.kurong.faketee.department.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.SQLException;
import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long>{
    List<Department> findAllByCorporation_Id(Long corId);
}
