package com.konai.kurong.faketee.utils.exception.custom.position;

import com.konai.kurong.faketee.utils.exception.ExceptionId;
import com.konai.kurong.faketee.utils.exception.custom.CustomRuntimeException;

public class PositionNotFoundException extends CustomRuntimeException {

    public PositionNotFoundException(){

        super(ExceptionId.POS_NOT_FOUND.getExceptionId());
    }

    public PositionNotFoundException(String msg){

        super(msg);
    }
}
