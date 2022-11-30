package com.konai.kurong.faketee.utils.exception.custom.auth;

import com.konai.kurong.faketee.utils.exception.ExceptionId;
import com.konai.kurong.faketee.utils.exception.custom.CustomRuntimeException;

public class NoUserFoundException extends CustomRuntimeException {

    public NoUserFoundException(){

        super(ExceptionId.USER_NOT_FOUND.getExceptionId());
    }

    public NoUserFoundException(String message){

        super(message);
    }
}
