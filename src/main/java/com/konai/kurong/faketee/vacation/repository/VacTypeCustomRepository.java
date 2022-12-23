package com.konai.kurong.faketee.vacation.repository;

import com.konai.kurong.faketee.vacation.dto.VacTypeResponseDto;
import com.konai.kurong.faketee.vacation.entity.VacType;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VacTypeCustomRepository {

    List<VacType> findAllByCorId(@Param("corId") Long corId);
    List<VacType> findAllByVacGroupId(@Param("vacGroupId") Long vacGroupId);
    void deleteByVacGroupId(@Param("vacGroupId") Long vacGroupId);
}
