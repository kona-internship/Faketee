package com.konai.kurong.faketee.vacation.repository.vac_type;

import com.konai.kurong.faketee.vacation.dto.VacTypeResponseDto;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VacTypeCustomRepository {

    List<VacTypeResponseDto> findAllByCorId(@Param("corId") Long corId);
    List<VacTypeResponseDto> findAllByVacGroupId(@Param("vacGroupId") Long vacGroupId);
}
