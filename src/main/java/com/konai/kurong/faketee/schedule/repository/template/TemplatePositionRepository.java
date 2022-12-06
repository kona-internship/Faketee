package com.konai.kurong.faketee.schedule.repository.template;

import com.konai.kurong.faketee.schedule.entity.TemplatePosition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplatePositionRepository extends JpaRepository<TemplatePosition, Long>, TemplatePositionCustomRepository {
}
