package com.konai.kurong.faketee.utils.exception.custom.schedule;

import com.konai.kurong.faketee.utils.exception.ExceptionId;
import com.konai.kurong.faketee.utils.exception.custom.CustomRuntimeException;

public class TemplatePositionNotFoundException extends CustomRuntimeException {

    public TemplatePositionNotFoundException(){

        super(ExceptionId.TMP_POS_NOT_FOUND.getExceptionId());
    }

    public TemplatePositionNotFoundException(String msg){

        super(msg);
    }
}
