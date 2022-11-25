package com.konai.kurong.faketee.location.repository;

import com.konai.kurong.faketee.location.entity.Location;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.konai.kurong.faketee.location.entity.QLocation.location;


@Slf4j
@RequiredArgsConstructor
public class QuerydslLocRepositoryImpl implements QuerydslLocRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Location> findLocationsByIds(List<Long> locIdList) {
        return jpaQueryFactory
                .select(location)
                .from(location)
                .where(location.id.in(locIdList))
                .fetch();
    }
}
