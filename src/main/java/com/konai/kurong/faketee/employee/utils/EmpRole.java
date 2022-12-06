package com.konai.kurong.faketee.employee.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmpRole {

    ADMIN("최고관리자"),
    GENERAL_MANAGER("총괄관리자"),
    GROUP_MANAGER("조직관리자"),
    EMPLOYEE("직원");

    private final String role;
}