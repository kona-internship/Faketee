package com.konai.kurong.faketee.utils.exception.custom.empauth;

import com.konai.kurong.faketee.utils.exception.ExceptionId;
import com.konai.kurong.faketee.utils.exception.custom.CustomRuntimeException;

public class EmpNotPermitException extends CustomRuntimeException {
    public EmpNotPermitException(){
        super(ExceptionId.EMP_AUTH_NOT_PERMIT.getExceptionId());
    }
}
