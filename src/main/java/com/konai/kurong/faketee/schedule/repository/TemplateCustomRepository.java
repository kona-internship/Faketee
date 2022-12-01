package com.konai.kurong.faketee.schedule.repository;

import com.konai.kurong.faketee.schedule.dto.TemplateResponseDto;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TemplateCustomRepository {

    List<TemplateResponseDto> findAllByCorId(@Param("corId") Long corId);
}
