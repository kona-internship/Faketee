package com.konai.kurong.faketee.vacation.repository;

import com.konai.kurong.faketee.vacation.entity.VacRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacRequestRepository extends JpaRepository<VacRequest, Long> {
}
