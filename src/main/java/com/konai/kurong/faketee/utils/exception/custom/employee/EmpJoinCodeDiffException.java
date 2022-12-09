package com.konai.kurong.faketee.utils.exception.custom.employee;

import com.konai.kurong.faketee.utils.exception.ExceptionId;
import com.konai.kurong.faketee.utils.exception.custom.CustomRuntimeException;

public class EmpJoinCodeDiffException extends CustomRuntimeException {
    public EmpJoinCodeDiffException() {
        super(ExceptionId.EMP_JOIN_CODE_DIFF.getExceptionId());
    }
}
