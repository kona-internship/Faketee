package com.konai.kurong.faketee.attend.repository;

import com.konai.kurong.faketee.attend.entity.AttendRequest;
import com.konai.kurong.faketee.attend.utils.AttendRequestVal;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface QuerydslAtdReqRepository {
    AttendRequest findAttendRequestByEmpDateVal(Long empId, LocalDate atdReqDate);

    Optional<AttendRequest> findAttendRequestByDraftVal(Long draftId);

    List<AttendRequest> getAtdReqByDraftId(Long draftId);
}
