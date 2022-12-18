package com.konai.kurong.faketee.attend.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;

import static com.konai.kurong.faketee.attend.entity.QAttend.attend;

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
}
