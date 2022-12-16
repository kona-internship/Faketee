package com.konai.kurong.faketee.attend.repository;

import com.konai.kurong.faketee.attend.entity.Attend;

import java.time.LocalTime;

public interface QuerydslAtdRepository {
    void updateAtdEndTime(Long scheInfoId, LocalTime endTime);
}
