package com.konai.kurong.faketee.vacation.repository.vac_info;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class VacInfoCustomRepositoryImpl implements VacInfoCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;
}
