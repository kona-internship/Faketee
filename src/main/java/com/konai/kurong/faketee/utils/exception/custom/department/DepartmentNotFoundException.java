package com.konai.kurong.faketee.utils.exception.custom.department;

import com.konai.kurong.faketee.utils.exception.ExceptionId;
import com.konai.kurong.faketee.utils.exception.custom.CustomRuntimeException;

public class DepartmentNotFoundException extends CustomRuntimeException {

    public DepartmentNotFoundException() {

        super(ExceptionId.DEP_NOT_FOUND.getExceptionId());
    }

    public DepartmentNotFoundException(String msg) {

        super(msg);
    }
}
