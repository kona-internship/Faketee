package com.konai.kurong.faketee.utils.exception.custom.vacation;

import com.konai.kurong.faketee.utils.exception.ExceptionId;
import com.konai.kurong.faketee.utils.exception.custom.CustomRuntimeException;

public class VacationInfoNotFoundException extends CustomRuntimeException {

    public VacationInfoNotFoundException(){

        super(ExceptionId.VAC_INFO_NOT_FOUND.getExceptionId());
    }

    public VacationInfoNotFoundException(String msg){

        super(msg);
    }
}
