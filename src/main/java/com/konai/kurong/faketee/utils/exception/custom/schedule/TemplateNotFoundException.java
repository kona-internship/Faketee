package com.konai.kurong.faketee.utils.exception.custom.schedule;

import com.konai.kurong.faketee.utils.exception.ExceptionId;
import com.konai.kurong.faketee.utils.exception.custom.CustomRuntimeException;

public class TemplateNotFoundException extends CustomRuntimeException {

    public TemplateNotFoundException(){

        super(ExceptionId.TMP_NOT_FOUND.getExceptionId());
    }

    public TemplateNotFoundException(String msg){

        super(msg);
    }
}
