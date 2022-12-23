package com.konai.kurong.faketee.schedule.repository.schedule;

import com.konai.kurong.faketee.attend.entity.AttendRequest;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.konai.kurong.faketee.schedule.entity.QScheduleInfo.scheduleInfo;

@RequiredArgsConstructor
public class QuerydslScheduleInfoRepositoryImpl implements QuerydslScheduleInfoRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<String> getSchInfoStateListByAtdReq(AttendRequest atdReq){
        return jpaQueryFactory
                .select(scheduleInfo.state)
                .from(scheduleInfo)
                .where(scheduleInfo.employee.id.eq(atdReq.getEmployee().getId()).and(scheduleInfo.date.eq(atdReq.getAtdReqDate())))
                .fetch();
    }


}
