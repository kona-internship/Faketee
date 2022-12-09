package com.konai.kurong.faketee.location.repository;

import com.konai.kurong.faketee.location.entity.Location;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface LocationRepository extends JpaRepository<Location, Long>, QuerydslLocRepository {
    List<Location> findLocationByCorporationIdOrderById(Long corId);
    List<Location> findLocationByIdIn(List<Long> ids);
}
