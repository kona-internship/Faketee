package com.konai.kurong.faketee.department.repository;

import com.konai.kurong.faketee.department.entity.Department;

public interface QuerydslDepRepository {
    Department getDepartmentWithoutSuper(Long depId);
}
