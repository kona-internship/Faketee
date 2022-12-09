package com.konai.kurong.faketee.utils.exception.custom.employee;

import com.konai.kurong.faketee.utils.exception.ExceptionId;
import com.konai.kurong.faketee.utils.exception.custom.CustomRuntimeException;

public class EmpUserDuplException extends CustomRuntimeException {
    public EmpUserDuplException() {
        super(ExceptionId.EMP_USER_DUPL.getExceptionId());
    }
}
