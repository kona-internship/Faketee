package com.konai.kurong.faketee.schedule.repository.schedule;

import com.konai.kurong.faketee.attend.entity.AttendRequest;

import java.util.List;

public interface QuerydslScheduleInfoRepository {
    List<String> getSchInfoStateListByAtdReq(AttendRequest atdReq);
}
