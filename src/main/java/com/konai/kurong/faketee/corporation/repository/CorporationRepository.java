package com.konai.kurong.faketee.corporation.repository;

import com.konai.kurong.faketee.corporation.entity.Corporation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorporationRepository extends JpaRepository<Corporation, Long>, CorporationCustomRepository {
}
