package com.konai.kurong.faketee.attend.repository;

import com.konai.kurong.faketee.attend.entity.AttendRequest;
import com.konai.kurong.faketee.attend.utils.AttendRequestVal;
import com.konai.kurong.faketee.employee.repository.QuerydslEmpRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Optional;

import static com.konai.kurong.faketee.attend.entity.QAttendRequest.attendRequest;

@RequiredArgsConstructor
public class QuerydslAtdReqRepositoryImpl implements QuerydslAtdReqRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public AttendRequest findAttendRequestByEmpDateVal(Long empId, LocalDate atdReqDate) {
        return jpaQueryFactory
                .select(attendRequest)
                .from(attendRequest)
                .where(attendRequest.employee.id.eq(empId),
                        attendRequest.atdReqDate.eq(atdReqDate),
//                        attendRequest.draft.id.eq(draftId),
                        attendRequest.val.eq(AttendRequestVal.T))
                .fetchOne();
    }

    @Override
    public Optional<AttendRequest> findAttendRequestByDraftVal(Long draftId) {
        return Optional.ofNullable(jpaQueryFactory
                .select(attendRequest)
                .from(attendRequest)
                .where(attendRequest.draft.id.eq(draftId),
                        attendRequest.val.eq(AttendRequestVal.T))
                .fetchOne());
    }
}