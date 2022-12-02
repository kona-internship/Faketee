package com.konai.kurong.faketee.schedule.repository;

import com.konai.kurong.faketee.schedule.entity.ScheduleInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleInfoRepository extends JpaRepository<ScheduleInfo, Long> {
}
