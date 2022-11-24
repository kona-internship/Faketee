package com.konai.kurong.faketee.location.repository;

import com.konai.kurong.faketee.location.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
