package com.konai.kurong.faketee.utils.exception.custom.auth;

import com.konai.kurong.faketee.utils.exception.ExceptionId;

public class NoEmailAuthFoundException extends RuntimeException {
    public NoEmailAuthFoundException() {

        super(ExceptionId.EMAIL_NOT_FOUND.getExceptionId());
    }

    public NoEmailAuthFoundException(String message) {

        super(message);
    }
}
