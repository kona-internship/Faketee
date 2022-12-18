package com.konai.kurong.faketee.utils.exception.custom.attend.request;

import com.konai.kurong.faketee.utils.exception.ExceptionId;
import com.konai.kurong.faketee.utils.exception.custom.CustomRuntimeException;

public class DraftNotWaitException extends CustomRuntimeException {

    public DraftNotWaitException() {
        super(ExceptionId.ATTEND_REQUEST_DRAFT_NOT_WAIT.getExceptionId());
    }
}
