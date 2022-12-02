package com.konai.kurong.faketee.schedule.repository;

import com.konai.kurong.faketee.schedule.entity.ScheduleType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.konai.kurong.faketee.schedule.entity.QScheduleType.scheduleType;

@RequiredArgsConstructor
@Repository
public class ScheduleTypeCustomRepositoryImpl implements ScheduleTypeCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public ScheduleType findByNameAndCorId(String name, Long corId) {

        return jpaQueryFactory
                .select(scheduleType)
                .from(scheduleType)
                .where(scheduleType.corporation.id.eq(corId))
                .where(scheduleType.name.eq(name))
                .fetchOne();
    }
}
