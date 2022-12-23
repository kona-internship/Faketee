package com.konai.kurong.faketee.vacation.repository;

import com.konai.kurong.faketee.vacation.entity.VacGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacGroupRepository extends JpaRepository<VacGroup, Long>, VacGroupCustomRepository {
}
