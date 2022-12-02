package com.konai.kurong.faketee.schedule.repository;

import com.konai.kurong.faketee.schedule.entity.ScheduleType;
import org.springframework.data.repository.query.Param;

public interface ScheduleTypeCustomRepository {

    ScheduleType findByNameAndCorId(@Param("name") String name, @Param("corId") Long corId);
}
