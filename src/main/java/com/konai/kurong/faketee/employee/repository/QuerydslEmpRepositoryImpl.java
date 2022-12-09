package com.konai.kurong.faketee.employee.repository;

import com.konai.kurong.faketee.employee.entity.Employee;
import com.konai.kurong.faketee.employee.entity.QEmployee;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.konai.kurong.faketee.employee.entity.QEmployee.employee;

@Slf4j
@RequiredArgsConstructor
public class QuerydslEmpRepositoryImpl implements QuerydslEmpRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Employee> getEmployeeByUserAndCorAndVal(Long usrId, Long corId, String val){
        return jpaQueryFactory
                .selectFrom(employee)
                .where(employee.user.id.eq(usrId), employee.corporation.id.eq(corId), employee.val.eq(val))
                .fetch();
    }
}