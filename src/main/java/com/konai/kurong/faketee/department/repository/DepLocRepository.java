package com.konai.kurong.faketee.department.repository;

import com.konai.kurong.faketee.department.entity.DepLoc;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DepLocRepository extends JpaRepository<DepLoc, Long>, QuerydslDepLocRepository {

    @EntityGraph(attributePaths = {"location", "department"})
    List<DepLoc> findAllByDepartment_Id(Long depId);

    Optional<DepLoc> findDepLocByLocationId(Long locId);

    void deleteDepLocByDepartmentId(Long depId);

    void deleteAllByDepartment_Id(Long depId);

}
