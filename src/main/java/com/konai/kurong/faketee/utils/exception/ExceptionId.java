package com.konai.kurong.faketee.utils.exception;

public enum ExceptionId {

//    ===============USER=================
    USER_NOT_FOUND("400_001_001"),
    EMAIL_NOT_FOUND("400_001_002"),

//    ===============DEP=================
    DEP_ALREADY_EXIST("400_002_001"),

//    ===============LOC=================

    CONNECT_DEP_EXIST("400_003_001");

    private final String exceptionId;

    ExceptionId(String errorId){
        this.exceptionId = errorId;
    }

    public String getExceptionId() {
        return exceptionId;
    }
}
