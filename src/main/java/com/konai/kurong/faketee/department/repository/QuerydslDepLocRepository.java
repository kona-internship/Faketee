package com.konai.kurong.faketee.department.repository;

import com.konai.kurong.faketee.department.entity.DepLoc;

import java.util.List;

public interface QuerydslDepLocRepository {
    Long deleteDepLocsByDepIds(List<Long> depIdList);

    List<DepLoc> getDepLocsByDepIdAndCorId(Long depId);
}
