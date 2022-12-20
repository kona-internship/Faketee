package com.konai.kurong.faketee.draft.repository;

import com.konai.kurong.faketee.draft.entity.Draft;
import com.konai.kurong.faketee.draft.utils.DraftStateCode;
import com.konai.kurong.faketee.vacation.entity.VacDateRequest;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;

import java.util.List;

import static com.konai.kurong.faketee.draft.entity.QDraft.draft;
import static com.konai.kurong.faketee.employee.entity.QEmployee.employee;

@Slf4j
@RequiredArgsConstructor
public class QuerydslDraftRepositoryImpl implements QuerydslDraftRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Draft> getDraftsWithRequestsByApproverAndStateCode(Long apvlEmpId, List<DraftStateCode> draftStateCodeList) {
        List<Draft> draftList =  jpaQueryFactory
                .selectFrom(draft)
                .join(draft.requestEmployee, employee)
                .where((draft.approvalEmp1.id.eq(apvlEmpId).or(draft.approvalEmpFin.id.eq(apvlEmpId))).and(draft.stateCode.in(draftStateCodeList)))
                .fetch();
        draftList.stream().map(Draft::getVacDateRequestList).forEach(Hibernate::initialize);
        log.info(">>>>>>>>>>>>>>>>querydsl repo: "+draftList);
        draftList.stream().map(Draft::getVacDateRequestList).forEach(vac->{log.info(">>>>>>>>>>>>>>>>querydsl repo: "+vac);});
        draftList.stream().forEach(draft1 -> {log.info(">>>>>>>>>>>>>>>>querydsl repo: "+draft1.getVacDateRequestList());});
        for(Draft draft : draftList){
            Hibernate.initialize(draft.getVacDateRequestList());
            for(VacDateRequest request : draft.getVacDateRequestList()){
                Hibernate.initialize(request.getVacType());
                log.info(">>>>>>>>>>>>>>>>querydsl repo vac: "+request);
            }
        }
        return draftList;
    }

    @Override
    public List<Draft> getDraftsWithRequestsByEmployeeAndStateCode(Long empId, List<DraftStateCode> draftStateCodeList){
        List<Draft> draftList =  jpaQueryFactory
                .selectFrom(draft)
                .join(draft.requestEmployee, employee)
                .where(draft.requestEmployee.id.eq(empId).and(draft.stateCode.in(draftStateCodeList)))
                .fetch();
        draftList.stream().map(Draft::getVacDateRequestList).forEach(Hibernate::initialize);
        return draftList;
    }

    @Override
    public void updateDraftStateCode(Long draftId, DraftStateCode draftStateCode){
        jpaQueryFactory
                .update(draft)
                .set(draft.stateCode, draftStateCode)
                .where(draft.id.eq(draftId))
                .execute();
    }

    @Override
    public List<Long> getDraftIdsByEmployeeId(Long empId){
        return jpaQueryFactory
                .select(draft.id)
                .from(draft)
                .where(draft.requestEmployee.id.eq(empId))
                .fetch();
    }
}
