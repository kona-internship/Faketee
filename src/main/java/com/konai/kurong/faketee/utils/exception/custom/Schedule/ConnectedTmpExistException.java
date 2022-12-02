package com.konai.kurong.faketee.utils.exception.custom.Schedule;

import com.konai.kurong.faketee.utils.exception.ExceptionId;
import com.konai.kurong.faketee.utils.exception.custom.CustomRuntimeException;

public class ConnectedTmpExistException extends CustomRuntimeException {

    public ConnectedTmpExistException() {
        super(ExceptionId.CONNECT_TMP_EXIST.getExceptionId());
    }
    public ConnectedTmpExistException(String msg) {
        super(msg);
    }
}
