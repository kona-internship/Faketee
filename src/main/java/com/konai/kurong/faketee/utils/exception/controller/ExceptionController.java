package com.konai.kurong.faketee.utils.exception.controller;

import com.konai.kurong.faketee.utils.exception.custom.department.LowDepAlreadyExistException;
import com.konai.kurong.faketee.utils.exception.response.CustomException;
import com.konai.kurong.faketee.utils.exception.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(LowDepAlreadyExistException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ExceptionResponse lowDepAlreadyExist(LowDepAlreadyExistException exception) {
        return getExceptionResponse(exception);
    }


    private ExceptionResponse getExceptionResponse(Exception exception) {
            ExceptionResponse errorResponse = ExceptionResponse.builder()
                .message("exception")
                .build();

        errorResponse.addError(CustomException.builder()
                .message(exception.getMessage())
                .build());
        return errorResponse;
    }
}
