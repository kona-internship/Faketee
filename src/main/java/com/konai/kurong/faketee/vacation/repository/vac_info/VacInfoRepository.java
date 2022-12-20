package com.konai.kurong.faketee.vacation.repository.vac_info;

import com.konai.kurong.faketee.vacation.entity.VacInfo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VacInfoRepository extends JpaRepository<VacInfo, Long>, VacInfoCustomRepository {
}
