package com.konai.kurong.faketee.utils.exception;

public enum ExceptionId {
    DEP_ALREADY_EXIST("400_001_001");

    private final String exceptionId;

    ExceptionId(String errorId){
        this.exceptionId = errorId;
    }

    public String getExceptionId() {
        return exceptionId;
    }
}
