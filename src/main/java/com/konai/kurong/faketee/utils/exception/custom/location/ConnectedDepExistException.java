package com.konai.kurong.faketee.utils.exception.custom.location;

import com.konai.kurong.faketee.utils.exception.ExceptionId;
import com.konai.kurong.faketee.utils.exception.custom.CustomRuntimeException;

public class ConnectedDepExistException extends CustomRuntimeException {
    public ConnectedDepExistException(){
        super(ExceptionId.CONNECT_DEP_EXIST.getExceptionId());
    }
}
