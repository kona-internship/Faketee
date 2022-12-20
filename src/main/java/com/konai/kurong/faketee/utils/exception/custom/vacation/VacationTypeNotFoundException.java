package com.konai.kurong.faketee.utils.exception.custom.vacation;

import com.konai.kurong.faketee.utils.exception.ExceptionId;
import com.konai.kurong.faketee.utils.exception.custom.CustomRuntimeException;

public class VacationTypeNotFoundException extends CustomRuntimeException {

    public VacationTypeNotFoundException(){

        super(ExceptionId.VAC_TYPE_NOT_FOUND.getExceptionId());
    }

    public VacationTypeNotFoundException(String msg){

        super(msg);
    }
}
