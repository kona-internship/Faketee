package com.konai.kurong.faketee.utils.exception;

public enum ExceptionId {

//    ===============USER=================
    USER_NOT_FOUND("400_001_001"),
    EMAIL_NOT_FOUND("400_001_002"),


//    ===============DEP=================
    DEP_ALREADY_EXIST("400_002_001"),

    DEP_NOT_FOUND("400_002_002"),


//    ===============LOC=================
    CONNECT_DEP_EXIST("400_003_001"),


//    ===============SCH=================
    CONNECT_TMP_EXIST("400_004_001"),
    SCH_TYPE_NOT_FOUND("400_004_002"),
    TMP_DEP_NOT_FOUND("400_004_003"),
    TMP_POS_NOT_FOUND("400_004_004"),
    TMP_NOT_FOUND("400_004_005"),


//    ===============POS=================
    POS_NOT_FOUND("400_005_001"),


//    ===============EMP_AUTH=================
    EMP_AUTH_COR_DIFF("400_006_001"),
    EMP_AUTH_NOT_PERMIT("400_006_002"),
    EMP_AUTH_DEP_DIFF("400_006_003"),

//    ===============EMP=================
    EMP_JOIN_EMAIL_DIFF("400_007_001"),
    EMP_USER_DUPL("400_007_002"),
    EMP_JOIN_CODE_DIFF("400_007_003"),

//    ===============VAC=================

    VAC_TYPE_NOT_FOUND("400_008_001"),
    VAC_GROUP_NOT_FOUND("400_008_002"),
    VAC_INFO_NOT_FOUND("400_008_003"),
    VAC_DATE_REQUEST_NOT_FOUND("400_008_004"),

//    ===============ATTEND_REQUEST=================
    ATTEND_REQUEST_DRAFT_NOT_WAIT("400_010_001");


    private final String exceptionId;

    ExceptionId(String errorId){
        this.exceptionId = errorId;
    }

    public String getExceptionId() {
        return exceptionId;
    }
}
