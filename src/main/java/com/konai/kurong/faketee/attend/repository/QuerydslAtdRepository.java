package com.konai.kurong.faketee.attend.repository;

import com.konai.kurong.faketee.attend.entity.Attend;
import com.konai.kurong.faketee.attend.entity.AttendRequest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface QuerydslAtdRepository {
    void updateAtdEndTime(Long scheInfoId, LocalTime endTime);

    List<Attend> getAttendByMonth(LocalDate startDate, LocalDate lastDate);
}
