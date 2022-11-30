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

    /**
     * 조직 ID들을 FK로 가지고 있는 DEP_LOC 행들을 전부 지운다.
     *
     * @param depIdList
     * @return
     */
    @Override
    public Long deleteDepLocsByDepIds(List<Long> depIdList){
        return jpaQueryFactory
                .delete(depLoc)
                .where(depLoc.department.id.in(depIdList))
                .execute();
    }
}
