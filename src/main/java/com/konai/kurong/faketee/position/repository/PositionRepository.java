package com.konai.kurong.faketee.position.repository;

import com.konai.kurong.faketee.position.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PositionRepository extends JpaRepository<Position, Long> {
    List<Position> findAllByCorporation_Id(Long corId);
}
