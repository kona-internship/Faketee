package com.konai.kurong.faketee.utils.exception;

public enum ExceptionId {

//    ===============USER=================
    USER_NOT_FOUND("400_001_001"),
    EMAIL_NOT_FOUND("400_001_002"),

//    ===============DEP=================
    DEP_ALREADY_EXIST("400_002_001"),

//    ===============EMP=================
    EMP_AUTH_COR_DIFF("400_003_001"),
    EMP_AUTH_NOT_PERMIT("400_003_002"),
    EMP_AUTH_DEP_DIFF("400_003_003");




    private final String exceptionId;

    ExceptionId(String errorId){
        this.exceptionId = errorId;
    }

    public String getExceptionId() {
        return exceptionId;
    }
}
