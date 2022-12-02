package com.konai.kurong.faketee.schedule.repository;

import com.konai.kurong.faketee.schedule.entity.ScheduleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleTypeRepository extends JpaRepository<ScheduleType, Long>, ScheduleTypeCustomRepository {
    List<ScheduleType> findAllByCorporationId(Long corId);
}
