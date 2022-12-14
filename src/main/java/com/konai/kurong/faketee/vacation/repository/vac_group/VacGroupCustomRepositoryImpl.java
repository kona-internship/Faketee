package com.konai.kurong.faketee.vacation.repository.vac_group;

import com.konai.kurong.faketee.vacation.dto.QVacGroupResponseDto;
import com.konai.kurong.faketee.vacation.dto.VacGroupResponseDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.konai.kurong.faketee.vacation.entity.QVacGroup.vacGroup;
import static com.konai.kurong.faketee.corporation.entity.QCorporation.corporation;

@RequiredArgsConstructor
@Repository
public class VacGroupCustomRepositoryImpl implements VacGroupCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<VacGroupResponseDto> findAllByCorId(Long corId){

        return jpaQueryFactory
                .select(new QVacGroupResponseDto(vacGroup))
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