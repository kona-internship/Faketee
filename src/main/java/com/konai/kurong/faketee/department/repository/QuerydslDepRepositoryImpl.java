package com.konai.kurong.faketee.department.repository;

import com.konai.kurong.faketee.department.entity.Department;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.konai.kurong.faketee.department.entity.QDepartment.department;

@Slf4j
@RequiredArgsConstructor
public class QuerydslDepRepositoryImpl implements QuerydslDepRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Department getDepartmentWithoutSuper(Long depId){
         Tuple result = jpaQueryFactory
                .select(department.id, department.level, department.name)
                .from(department)
                .where(department.id.eq(depId))
                .fetchOne();
         return Department.builder()
                 .id(result.get(department.id))
                 .level(result.get(department.level))
                 .name(result.get(department.name))
                 .build();
    }
}
