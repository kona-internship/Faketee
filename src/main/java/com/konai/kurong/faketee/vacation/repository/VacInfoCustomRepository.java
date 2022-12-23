package com.konai.kurong.faketee.vacation.repository;

import com.konai.kurong.faketee.vacation.entity.VacInfo;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VacInfoCustomRepository {

    List<VacInfo> findAllByEmpId(@Param("empId") Long empId);
    List<VacInfo> findAllByCorId(@Param("corId") Long corId);
    List<VacInfo> findAllByDepId(@Param("depId") Long depId);
    VacInfo updateByEmpAndVacGroupId(@Param("empId") Long empId, @Param("vacGroupId") Long vacGroupId);
    void deleteByVacGroupId(@Param("vacGroupId") Long vacGroupId);

}
