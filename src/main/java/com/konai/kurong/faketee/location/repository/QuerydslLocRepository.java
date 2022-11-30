package com.konai.kurong.faketee.location.repository;

import com.konai.kurong.faketee.location.entity.Location;

import java.util.List;

public interface QuerydslLocRepository {
    List<Location> findLocationsByIds(List<Long> locIdList);
}
