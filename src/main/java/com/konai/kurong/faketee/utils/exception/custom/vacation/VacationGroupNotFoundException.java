package com.konai.kurong.faketee.utils.exception.custom.vacation;

import com.konai.kurong.faketee.utils.exception.ExceptionId;
import com.konai.kurong.faketee.utils.exception.custom.CustomRuntimeException;

public class VacationGroupNotFoundException extends CustomRuntimeException {

    public VacationGroupNotFoundException(){

        super(ExceptionId.VAC_GROUP_NOT_FOUND.getExceptionId());
    }

    public VacationGroupNotFoundException(String msg){

        super(msg);
    }
}
