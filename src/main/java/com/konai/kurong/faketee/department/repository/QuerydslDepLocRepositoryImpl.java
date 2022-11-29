package com.konai.kurong.faketee.department.repository;

import com.konai.kurong.faketee.department.entity.DepLoc;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.konai.kurong.faketee.department.entity.QDepLoc.depLoc;
import static com.konai.kurong.faketee.department.entity.QDepartment.department;

@Slf4j
@RequiredArgsConstructor
public class QuerydslDepLocRepositoryImpl implements QuerydslDepLocRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long deleteDepLocsByDepIds(List<Long> depIdList){
        return jpaQueryFactory
                .delete(depLoc)
                .where(depLoc.department.id.in(depIdList))
                .execute();
    }
}
