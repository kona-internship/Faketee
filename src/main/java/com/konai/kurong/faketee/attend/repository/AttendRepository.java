package com.konai.kurong.faketee.attend.repository;

import com.konai.kurong.faketee.attend.entity.Attend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttendRepository extends JpaRepository<Attend, Long>, QuerydslAtdRepository {
    Optional<Attend> findAllByScheduleInfoId(Long infoId);
}
