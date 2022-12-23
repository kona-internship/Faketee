package com.konai.kurong.faketee.utils.exception.custom.schedule;

import com.konai.kurong.faketee.utils.exception.ExceptionId;
import com.konai.kurong.faketee.utils.exception.custom.CustomRuntimeException;

public class ConnectedAtdExistException extends CustomRuntimeException {
    public ConnectedAtdExistException() {
        super(ExceptionId.CONNECT_ATD_EXIST.getExceptionId());
    }
}
