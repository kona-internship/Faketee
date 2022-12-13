package com.konai.kurong.faketee.vacation.repository.vac_type;

import com.konai.kurong.faketee.vacation.entity.VacType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacTypeRepository extends JpaRepository<VacType, Long>, VacTypeCustomRepository {
}
