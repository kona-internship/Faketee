package com.konai.kurong.faketee.utils.exception.controller.attend;

import com.konai.kurong.faketee.utils.exception.controller.ExceptionController;
import com.konai.kurong.faketee.utils.exception.custom.attend.request.DraftNotWaitException;
import com.konai.kurong.faketee.utils.exception.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.konai.kurong.faketee")
public class AttendRequestExceptionController extends ExceptionController {

    @ExceptionHandler(DraftNotWaitException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ExceptionResponse draftNotWait(DraftNotWaitException exception) {
        return getExceptionResponse(exception);
    }
}
