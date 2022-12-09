package com.konai.kurong.faketee.utils.exception.controller;

import com.konai.kurong.faketee.utils.exception.response.CustomException;
import com.konai.kurong.faketee.utils.exception.response.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;



@Slf4j
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse unknown(Exception exception) {
        log.info(exception.toString());
        if (exception instanceof DataIntegrityViolationException) {
            DataIntegrityViolationException e = (DataIntegrityViolationException) exception;
            log.error("getLocalizedMessage"+ e.getLocalizedMessage());
            log.error("getLocalizedMessage"+ e.getCause().toString());

            log.error("getMessage: " + e.getMessage());
        }

        return getExceptionResponse(exception);
    }

    protected ExceptionResponse getExceptionResponse(Exception ... exceptions) {
        ExceptionResponse errorResponse = ExceptionResponse.builder()
                .message("exception")
                .build();
        for(Exception exception: exceptions) {
            errorResponse.addError(CustomException.builder()
                    .message(exception.getMessage())
                    .build());
        }
        return errorResponse;
    }
}
