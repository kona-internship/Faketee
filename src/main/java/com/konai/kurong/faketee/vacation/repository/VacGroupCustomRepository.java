package com.konai.kurong.faketee.vacation.repository;

import com.konai.kurong.faketee.vacation.dto.VacGroupResponseDto;
import com.konai.kurong.faketee.vacation.entity.VacGroup;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VacGroupCustomRepository {

    List<VacGroup> findAllByCorId(@Param("corId") Long corId);
}
