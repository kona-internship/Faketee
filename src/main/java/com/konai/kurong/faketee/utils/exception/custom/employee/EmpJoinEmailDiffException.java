package com.konai.kurong.faketee.utils.exception.custom.employee;

import com.konai.kurong.faketee.utils.exception.ExceptionId;
import com.konai.kurong.faketee.utils.exception.custom.CustomRuntimeException;

public class EmpJoinEmailDiffException extends CustomRuntimeException {
    public EmpJoinEmailDiffException() {
        super(ExceptionId.EMP_JOIN_EMAIL_DIFF.getExceptionId());
    }
}
