package com.konai.kurong.faketee.utils.exception.controller.schedule;

import com.konai.kurong.faketee.utils.exception.controller.ExceptionController;
import com.konai.kurong.faketee.utils.exception.custom.schedule.ConnectedAtdExistException;
import com.konai.kurong.faketee.utils.exception.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.konai.kurong.faketee.schedule")
public class ScheduleExceptionController extends ExceptionController {

    @ExceptionHandler(ConnectedAtdExistException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ExceptionResponse connectedAtdExistException(ConnectedAtdExistException exception) {
        return getExceptionResponse(exception);
    }

}
