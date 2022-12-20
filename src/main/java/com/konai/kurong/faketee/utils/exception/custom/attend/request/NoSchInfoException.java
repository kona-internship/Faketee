package com.konai.kurong.faketee.utils.exception.custom.attend.request;

import com.konai.kurong.faketee.utils.exception.ExceptionId;
import com.konai.kurong.faketee.utils.exception.custom.CustomRuntimeException;

public class NoSchInfoException extends CustomRuntimeException {

    public NoSchInfoException() {
        super(ExceptionId.ATTEND_REQUEST_NO_SCH_INFO.getExceptionId());
    }
}
