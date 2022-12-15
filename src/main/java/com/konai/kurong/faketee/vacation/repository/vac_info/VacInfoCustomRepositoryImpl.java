package com.konai.kurong.faketee.vacation.repository.vac_info;

import com.konai.kurong.faketee.vacation.entity.VacInfo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class VacInfoCustomRepositoryImpl implements VacInfoCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<VacInfo> findAllByEmpId(Long empId) {
        return null;
    }

    @Override
    public List<VacInfo> findAllByCorId(Long corId) {
        return null;
    }

    @Override
    public List<VacInfo> findAllByDepId(Long depId) {
        return null;
    }
}
