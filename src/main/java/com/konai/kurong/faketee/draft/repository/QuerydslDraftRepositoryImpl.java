package com.konai.kurong.faketee.draft.repository;

import com.konai.kurong.faketee.draft.entity.Draft;
import com.konai.kurong.faketee.draft.utils.DraftStateCode;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;

import java.util.List;

import static com.konai.kurong.faketee.draft.entity.QDraft.draft;

@Slf4j
@RequiredArgsConstructor
public class QuerydslDraftRepositoryImpl implements QuerydslDraftRepository{

    private final JPAQueryFactory jpaQueryFactory;

    public List<Draft> getDraftsWithRequestsByApproverAndStateCode(Long apvlEmpId, List<DraftStateCode> draftStateCodeList) {
        List<Draft> draftList =  jpaQueryFactory
                .selectFrom(draft)
                .where((draft.approvalEmp1.id.eq(apvlEmpId).or(draft.approvalEmpFin.id.eq(apvlEmpId))).and(draft.stateCode.in(draftStateCodeList)))
                .fetch();
        draftList.stream().map(Draft::getVacRequestList).forEach(Hibernate::initialize);
        return draftList;
    }

    public List<Draft> getDraftsWithRequestsByEmployeeAndStateCode(Long empId, List<DraftStateCode> draftStateCodeList){
        List<Draft> draftList =  jpaQueryFactory
                .selectFrom(draft)
                .where(draft.requestEmployee.id.eq(empId).and(draft.stateCode.in(draftStateCodeList)))
                .fetch();
        draftList.stream().map(Draft::getVacRequestList).forEach(Hibernate::initialize);
        return draftList;
    }

    public void updateDraftStateCode(Long draftId, DraftStateCode draftStateCode){
        jpaQueryFactory
                .update(draft)
                .set(draft.stateCode, draftStateCode)
                .where(draft.id.eq(draftId))
                .execute();
    }
}
