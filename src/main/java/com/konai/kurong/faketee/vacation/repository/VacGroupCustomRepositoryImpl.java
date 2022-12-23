package com.konai.kurong.faketee.vacation.repository;

import com.konai.kurong.faketee.vacation.entity.VacGroup;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.konai.kurong.faketee.vacation.entity.QVacGroup.vacGroup;

@RequiredArgsConstructor
@Repository
public class VacGroupCustomRepositoryImpl implements VacGroupCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<VacGroup> findAllByCorId(Long corId){

        return jpaQueryFactory
                .select(vacGroup)
                .from(vacGroup)
                .where(vacGroup.corporation.id.eq(corId))
                .orderBy(vacGroup.CRE_DTTM.asc())
                .fetch();

//        return jpaQueryFactory
//                .select(new QVacGroupResponseDto(vacGroup))
//                .from(vacGroup)
//                .join(vacGroup.corporation, corporation)
//                .fetchJoin() // FetchType.EAGER와 같은 방법
//                .where(corporation.id.eq(corId))
//                .fetch();
    }

}
