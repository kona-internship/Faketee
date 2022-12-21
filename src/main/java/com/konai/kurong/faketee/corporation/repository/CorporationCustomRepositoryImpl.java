package com.konai.kurong.faketee.corporation.repository;

import com.konai.kurong.faketee.corporation.entity.Corporation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.konai.kurong.faketee.corporation.entity.QCorporation.*;
import static com.konai.kurong.faketee.employee.entity.QEmployee.*;

@RequiredArgsConstructor
@Repository
public class CorporationCustomRepositoryImpl implements CorporationCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Corporation findByEmpId(Long empId) {

        return jpaQueryFactory
                .select(corporation)
                .from(employee)
                .where(employee.id.eq(empId))
                .fetchOne();
    }
}
