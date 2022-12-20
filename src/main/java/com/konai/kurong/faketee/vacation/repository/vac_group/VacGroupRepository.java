package com.konai.kurong.faketee.vacation.repository.vac_group;

import com.konai.kurong.faketee.vacation.entity.VacGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacGroupRepository extends JpaRepository<VacGroup, Long>, VacGroupCustomRepository {
}
