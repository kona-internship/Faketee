package com.konai.kurong.faketee.utils.exception.custom.schedule;

import com.konai.kurong.faketee.utils.exception.ExceptionId;
import com.konai.kurong.faketee.utils.exception.custom.CustomRuntimeException;

public class TemplateDepartmentNotFoundException extends CustomRuntimeException {

    public TemplateDepartmentNotFoundException() {

        super(ExceptionId.TMP_DEP_NOT_FOUND.getExceptionId());
    }

    public TemplateDepartmentNotFoundException(String msg) {

        super(msg);
    }
}
