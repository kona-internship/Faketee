package com.konai.kurong.faketee.schedule.repository.template;

import com.konai.kurong.faketee.schedule.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface TemplateRepository extends JpaRepository<Template, Long>, TemplateCustomRepository {

    Long countTemplateByScheduleTypeId(Long typeId);

}
