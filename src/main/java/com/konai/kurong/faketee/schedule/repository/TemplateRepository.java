package com.konai.kurong.faketee.schedule.repository;

import com.konai.kurong.faketee.schedule.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemplateRepository extends JpaRepository<Template, Long> {
    Long countTemplateByScheduleTypeId(Long typeId);
    List<Template> findTemplatesByScheduleTypeCorporationId(Long corId);

}
