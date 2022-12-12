package com.konai.kurong.faketee.schedule.repository.schedule;

import com.konai.kurong.faketee.schedule.entity.ScheduleInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleInfoRepository extends JpaRepository<ScheduleInfo, Long> {
    List<ScheduleInfo> findAllByDateAndEmployeeCorporationId(LocalDate date, Long corId);
}
