package com.konai.kurong.faketee.utils.exception.custom.vacation;

import com.konai.kurong.faketee.utils.exception.ExceptionId;
import com.konai.kurong.faketee.utils.exception.custom.CustomRuntimeException;

public class VacationDateRequestNotFoundException extends CustomRuntimeException {

    public VacationDateRequestNotFoundException(){

        super(ExceptionId.VAC_DATE_REQUEST_NOT_FOUND.getExceptionId());
    }

    public VacationDateRequestNotFoundException(String msg){

        super(msg);
    }
}
