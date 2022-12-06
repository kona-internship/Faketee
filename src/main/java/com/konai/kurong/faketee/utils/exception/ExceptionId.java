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

    POS_NOT_FOUND("400_005_001");



    private final String exceptionId;

    ExceptionId(String errorId){
        this.exceptionId = errorId;
    }

    public String getExceptionId() {
        return exceptionId;
    }
}
