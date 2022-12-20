package com.konai.kurong.faketee.employee.utils;

/**
 * DEP_LOW 자신의 하위조직인지 검증이 필요할 때
 * DRAFT_REQ 자신의 기안의 요청자인지 검증이 필요할 때
 * DRAFT_APVL 자신이 기안의 승인권자인지 검증이 필요할 때
 * NO_CHECK 검증이 필요없을 때
 */
public enum EmpAuthParamType {
    DEP_LOW,
    DRAFT_REQ,
    DRAFT_APVL,
    NO_CHECK
}
