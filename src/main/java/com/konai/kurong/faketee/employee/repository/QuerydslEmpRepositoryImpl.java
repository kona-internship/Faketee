package com.konai.kurong.faketee.employee.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class QuerydslEmpRepositoryImpl implements QuerydslEmpRepository {

    private final JPAQueryFactory jpaQueryFactory;
}