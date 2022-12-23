package com.konai.kurong.faketee.attend.repository;

import com.konai.kurong.faketee.attend.entity.Attend;
import com.konai.kurong.faketee.attend.entity.AttendRequest;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.konai.kurong.faketee.attend.entity.QAttend.attend;
import static com.konai.kurong.faketee.attend.entity.QAttendRequest.attendRequest;
import static com.konai.kurong.faketee.employee.entity.QEmployee.employee;

@Slf4j
@RequiredArgsConstructor
public class QuerydslAtdRepositoryImpl implements QuerydslAtdRepository{

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 퇴근 시간 업데이트
     * @param scheInfoId
     * @param endTime
     */
    @Override
    public void updateAtdEndTime(Long scheInfoId, LocalTime endTime) {
         jpaQueryFactory
                 .update(attend)
                 .set(attend.endTime, endTime)
                 .where(attend.scheduleInfo.id.eq(scheInfoId))
                 .execute();
    }

    @Override
    public List<Attend> getAttendByMonth(LocalDate startDate, LocalDate lastDate) {
        return jpaQueryFactory
                    .select(attend)
                    .from(attend)
                    .where(attend.date.between(startDate, lastDate))
                    .orderBy(attend.date.asc())
                    .fetch();
    }
}
