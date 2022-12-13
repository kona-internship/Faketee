package com.konai.kurong.faketee.vacation.repository.vac_type;

import com.konai.kurong.faketee.vacation.dto.QVacTypeResponseDto;
import com.konai.kurong.faketee.vacation.dto.VacTypeResponseDto;
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
    public List<VacTypeResponseDto> findAllByCorId(Long corId) {

        return jpaQueryFactory
                .select(new QVacTypeResponseDto(vacType))
                .from(vacType)
                .where(vacType.vacGroup.corporation.id.eq(corId))
                .orderBy(vacType.CRE_DTTM.asc())
                .fetch();
    }

    @Override
    public List<VacTypeResponseDto> findAllByVacGroupId(Long vacGroupId) {

        return jpaQueryFactory
                .select(new QVacTypeResponseDto(vacType))
                .from(vacType)
                .where(vacType.vacGroup.id.eq(vacGroupId))
                .orderBy(vacType.CRE_DTTM.asc())
                .fetch();
    }
}
