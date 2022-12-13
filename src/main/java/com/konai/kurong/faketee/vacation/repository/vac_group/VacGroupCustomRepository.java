package com.konai.kurong.faketee.vacation.repository.vac_group;

import com.konai.kurong.faketee.vacation.dto.VacGroupResponseDto;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VacGroupCustomRepository {

    List<VacGroupResponseDto> findAllByCorId(@Param("corId") Long corId);
}
