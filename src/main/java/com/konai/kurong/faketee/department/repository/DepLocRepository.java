package com.konai.kurong.faketee.department.repository;

import com.konai.kurong.faketee.department.entity.DepLoc;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepLocRepository extends JpaRepository<DepLoc, Long> {

    @EntityGraph(attributePaths = {"location", "department"})
    List<DepLoc> findAllByDepartment_Id(Long depId);
}
