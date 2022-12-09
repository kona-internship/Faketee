package com.konai.kurong.faketee.schedule.repository.template;

import com.konai.kurong.faketee.schedule.dto.TemplatePositionResponseDto;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TemplatePositionCustomRepository {

    List<TemplatePositionResponseDto> findAllByTmpId(@Param("tempId") Long tempId);
    void deleteByTmpId(@Param("tempId") Long tempId);
}
