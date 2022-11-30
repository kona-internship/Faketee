package com.konai.kurong.faketee.utils.exception.controller.auth;

import com.konai.kurong.faketee.utils.exception.controller.ExceptionController;
import com.konai.kurong.faketee.utils.exception.custom.auth.NoEmailAuthFoundException;
import com.konai.kurong.faketee.utils.exception.custom.auth.NoUserFoundException;
import com.konai.kurong.faketee.utils.exception.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.konai.kurong.faketee.auth")
public class AuthExceptionController extends ExceptionController {

    @ExceptionHandler(NoEmailAuthFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ExceptionResponse noEmailAuthFound(NoEmailAuthFoundException exception){
        return getExceptionResponse(exception);
    }

    @ExceptionHandler(NoUserFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ExceptionResponse noUserFoundException(NoUserFoundException exception){
        return getExceptionResponse(exception);
    }
}
