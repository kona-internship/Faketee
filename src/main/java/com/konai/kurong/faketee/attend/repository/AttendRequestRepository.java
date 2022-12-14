package com.konai.kurong.faketee.attend.repository;

import com.konai.kurong.faketee.attend.entity.AttendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendRequestRepository extends JpaRepository<AttendRequest, Long> {
}
