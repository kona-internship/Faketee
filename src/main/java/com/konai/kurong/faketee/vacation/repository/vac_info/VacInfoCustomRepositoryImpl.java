package com.konai.kurong.faketee.vacation.repository.vac_info;

import com.konai.kurong.faketee.vacation.entity.VacInfo;
import com.konai.kurong.faketee.vacation.entity.VacRequest;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import static com.konai.kurong.faketee.vacation.entity.QVacInfo.vacInfo;
import static com.konai.kurong.faketee.vacation.entity.QVacRequest.vacRequest;
import static com.konai.kurong.faketee.vacation.entity.QVacType.vacType;

@RequiredArgsConstructor
@Repository
public class VacInfoCustomRepositoryImpl implements VacInfoCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<VacInfo> findAllByEmpId(Long empId) {

        return jpaQueryFactory
                .selectFrom(vacInfo)
                .where(vacInfo.employee.id.eq(empId))
                .orderBy(vacInfo.CRE_DTTM.asc())
                .fetch();
    }

    @Override
    public List<VacInfo> findAllByCorId(Long corId) {

        return null;
    }

    @Override
    public List<VacInfo> findAllByDepId(Long depId) {
        return null;
    }

    @Override
    public VacInfo updateByEmpAndVacGroupId(Long empId, Long vacGroupId) {

        return jpaQueryFactory
                .selectFrom(vacInfo)
                .where(vacInfo.employee.id.eq(empId), vacInfo.vacGroup.id.eq(vacGroupId))
                .fetchOne();
    }

    @Override
    public void deleteByVacGroupId(Long vacGroupId) {

        jpaQueryFactory
                .delete(vacInfo)
                .where(vacInfo.vacGroup.id.eq(vacGroupId))
                .execute();
    }

    @Override
    public List<VacRequest> getVacReqByDraftId(Long draftId){
        return jpaQueryFactory
                .selectFrom(vacRequest)
                .join(vacRequest.vacType, vacType)
                .where(vacRequest.draft.id.eq(draftId))
                .fetch();
    }
}
