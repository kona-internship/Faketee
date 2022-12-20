package com.konai.kurong.faketee.employee.repository;

import com.konai.kurong.faketee.department.entity.Department;
import com.konai.kurong.faketee.employee.entity.Employee;
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
        if(usrId == null){
            return jpaQueryFactory
                    .selectFrom(employee)
                    .where(employee.user.id.isNull(), employee.corporation.id.eq(corId), employee.val.eq(val))
                    .fetch();
        }
        return jpaQueryFactory
                .selectFrom(employee)
                .where(employee.user.id.eq(usrId), employee.corporation.id.eq(corId), employee.val.eq(val))
                .fetch();
    }

    @Override
    public List<Employee> findByDepId(Long depId) {

        return jpaQueryFactory
                .selectFrom(employee)
                .where(employee.department.id.eq(depId), employee.val.eq("T"))
                .fetch();
    }

    @Override
    public List<Employee> findByUserId(Long userId) {

        return jpaQueryFactory
                .selectFrom(employee)
                .where(employee.user.id.eq(userId))
                .fetch();
    }

    public List<Employee> getEmployeeByDepAndPos(List<Long> deps, List<Long> pos) {
        return jpaQueryFactory
                .selectFrom(employee)
                .where(employee.department.id.in(deps).and(employee.position.id.in(pos)))
                .fetch();
    }
}