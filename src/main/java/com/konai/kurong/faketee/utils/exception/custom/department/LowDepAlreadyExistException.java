package com.konai.kurong.faketee.utils.exception.custom.department;

import com.konai.kurong.faketee.utils.exception.ExceptionId;
import com.konai.kurong.faketee.utils.exception.custom.CustomRuntimeException;

public class LowDepAlreadyExistException extends CustomRuntimeException {

    public LowDepAlreadyExistException(){
        super(ExceptionId.DEP_ALREADY_EXIST.getExceptionId());
    }
    public LowDepAlreadyExistException(String msg){
        super(msg);
    }

}
