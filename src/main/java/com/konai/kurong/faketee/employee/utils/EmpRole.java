package com.konai.kurong.faketee.employee.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum EmpRole {

    ADMIN("최고관리자"),
    GENERAL_MANAGER("총괄관리자"),
    GROUP_MANAGER("조직관리자"),
    EMPLOYEE("직원");

    private final String role;

    public static EmpRole convertToType(String stringRole){
        return Arrays.stream(values())
                .filter(empRole -> empRole.role.equals(stringRole))
                .findAny()
                .orElse(null);
    }
}