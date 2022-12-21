package com.konai.kurong.faketee.corporation.repository;

import com.konai.kurong.faketee.corporation.entity.Corporation;
import org.springframework.data.repository.query.Param;

public interface CorporationCustomRepository {

    Corporation findByEmpId(@Param("empId") Long empId);
}
