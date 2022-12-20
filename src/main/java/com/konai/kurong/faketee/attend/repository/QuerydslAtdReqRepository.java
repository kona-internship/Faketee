package com.konai.kurong.faketee.attend.repository;

import com.konai.kurong.faketee.attend.entity.AttendRequest;
import com.konai.kurong.faketee.attend.utils.AttendRequestVal;

import java.time.LocalDate;
import java.util.Optional;

public interface QuerydslAtdReqRepository {
    Optional<AttendRequest> findAttendRequestByEmpDateVal(Long empId, LocalDate atdReqDate);

    Optional<AttendRequest> findAttendRequestByDraftVal(Long draftId);
}
