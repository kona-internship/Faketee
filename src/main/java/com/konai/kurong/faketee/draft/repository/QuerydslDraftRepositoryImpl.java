package com.konai.kurong.faketee.draft.repository;

import com.konai.kurong.faketee.draft.entity.Draft;
import com.konai.kurong.faketee.draft.utils.DraftRequestType;
import com.konai.kurong.faketee.draft.utils.DraftStateCode;
import com.konai.kurong.faketee.vacation.entity.VacRequest;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.List;

import static com.konai.kurong.faketee.draft.entity.QDraft.draft;
import static com.konai.kurong.faketee.employee.entity.QEmployee.employee;

@Slf4j
@RequiredArgsConstructor
public class QuerydslDraftRepositoryImpl implements QuerydslDraftRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Draft> getDraftsWithRequestsByApproverAndStateCode(Long apvlEmpId) {
        List<Draft> draftList =  jpaQueryFactory
                .selectFrom(draft)
                .join(draft.requestEmployee, employee)
//                .where((draft.approvalEmp1.id.eq(apvlEmpId).and(draft.stateCode.eq(DraftStateCode.WAIT)))
//                        .or((draft.approvalEmpFin.id.eq(apvlEmpId).and(draft.stateCode.eq(DraftStateCode.APVL_1)).and(draft.approvalEmp1.isNull())))
//                        .or((draft.approvalEmpFin.id.eq(apvlEmpId).and(draft.stateCode.eq(DraftStateCode.WAIT)).and(draft.approvalEmp1.isNotNull())))
//                )
                .where((draft.approvalEmp1.id.eq(apvlEmpId).or(draft.approvalEmpFin.id.eq(apvlEmpId))).and(draft.stateCode.in(DraftStateCode.getWaitingForApproval()))
                )
                .fetch();
        log.info(">>>>>>>>>>>>>>>>querydsl repo: "+draftList);
//        draftList.stream().map(Draft::getVacRequestList).forEach(Hibernate::initialize);
//        draftList.stream().map(Draft::getVacRequestList).forEach(vac->{log.info(">>>>>>>>>>>>>>>>querydsl repo: "+vac);});
//        draftList.stream().forEach(draft1 -> {log.info(">>>>>>>>>>>>>>>>querydsl repo: "+draft1.getVacRequestList());});
        for(Draft draft : draftList){
            if(draft.getRequestType().equals(DraftRequestType.ATTENDANCE)){
                Hibernate.initialize(draft.getAtdRequestList());
            } else if (draft.getRequestType().equals(DraftRequestType.VACATION)) {
                Hibernate.initialize(draft.getVacRequestList());
                for(VacRequest request : draft.getVacRequestList()){
                    Hibernate.initialize(request.getVacType());
                    log.info(">>>>>>>>>>>>>>>>querydsl repo vac: "+request);
                }
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
        draftList.stream().map(Draft::getVacRequestList).forEach(Hibernate::initialize);
        draftList.stream().map(Draft::getAtdRequestList).forEach(Hibernate::initialize);
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

    @Override
    public void updateDraftStateCodeAndDateAndMessageByApvlEmp(Long draftId, DraftStateCode draftStateCode, LocalDateTime dateTime, String apvlMsg){
        JPAUpdateClause updateClause = jpaQueryFactory
                .update(draft)
                .set(draft.stateCode, draftStateCode)
                .set(draft.approvalDate, dateTime)
                .set(draft.approvalMessage, apvlMsg);
        if(draftStateCode.equals(DraftStateCode.APVL_FIN) || draftStateCode.equals(DraftStateCode.REJ_FIN)){
            updateClause
                    .where(draft.id.eq(draftId))
                    .execute();
        } else if (draftStateCode.equals(DraftStateCode.APVL_1) || draftStateCode.equals(DraftStateCode.REJ_1)) {
            updateClause
                    .where(draft.id.eq(draftId))
                    .execute();
        }
    }
}
