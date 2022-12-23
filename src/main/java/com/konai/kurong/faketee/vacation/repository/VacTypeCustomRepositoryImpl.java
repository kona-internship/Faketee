package com.konai.kurong.faketee.vacation.repository;

import com.konai.kurong.faketee.vacation.entity.VacType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.konai.kurong.faketee.vacation.entity.QVacType.vacType;

@RequiredArgsConstructor
@Repository
public class VacTypeCustomRepositoryImpl implements VacTypeCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<VacType> findAllByCorId(Long corId) {

        return jpaQueryFactory
                .select(vacType)
                .from(vacType)
                .where(vacType.vacGroup.corporation.id.eq(corId))
                .orderBy(vacType.vacGroup.id.asc())
                .fetch();
    }

    @Override
    public List<VacType> findAllByVacGroupId(Long vacGroupId) {

        return jpaQueryFactory
                .select(vacType)
                .from(vacType)
                .where(vacType.vacGroup.id.eq(vacGroupId))
                .orderBy(vacType.CRE_DTTM.asc())
                .fetch();
    }

    @Override
    public void deleteByVacGroupId(Long vacGroupId) {

        jpaQueryFactory
                .delete(vacType)
                .where(vacType.vacGroup.id.eq(vacGroupId))
                .execute();
    }
}
