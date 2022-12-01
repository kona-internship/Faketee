package com.konai.kurong.faketee.employee.utils;

public enum EmpRole {

    MASTER("최고관리자"),
    GENERAL_MANAGER("총괄관리자"),
    GROUP_MANAGER("조직관리자"),
    EMPLOYEE("직원");

    private final String role;

    EmpRole(String role){
        this.role = role;
    }

    public String getEmpRole() {
        return role;
    }
}
