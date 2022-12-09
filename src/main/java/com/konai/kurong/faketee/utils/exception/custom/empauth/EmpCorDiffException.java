package com.konai.kurong.faketee.utils.exception.custom.empauth;

import com.konai.kurong.faketee.utils.exception.ExceptionId;
import com.konai.kurong.faketee.utils.exception.custom.CustomRuntimeException;

public class EmpCorDiffException extends CustomRuntimeException {

    public EmpCorDiffException(){
        super(ExceptionId.EMP_AUTH_COR_DIFF.getExceptionId());
    }
}
