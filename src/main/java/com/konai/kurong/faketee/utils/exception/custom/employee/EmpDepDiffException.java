package com.konai.kurong.faketee.utils.exception.custom.employee;

import com.konai.kurong.faketee.utils.exception.ExceptionId;
import com.konai.kurong.faketee.utils.exception.custom.CustomRuntimeException;

public class EmpDepDiffException extends CustomRuntimeException {
    public EmpDepDiffException(){
        super(ExceptionId.EMP_AUTH_DEP_DIFF.getExceptionId());
    }
}
