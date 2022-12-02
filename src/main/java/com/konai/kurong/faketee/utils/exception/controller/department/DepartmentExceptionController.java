package com.konai.kurong.faketee.utils.exception.controller.department;

import com.konai.kurong.faketee.utils.exception.controller.ExceptionController;
import com.konai.kurong.faketee.utils.exception.custom.department.LowDepAlreadyExistException;
import com.konai.kurong.faketee.utils.exception.response.CustomException;
import com.konai.kurong.faketee.utils.exception.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.konai.kurong.faketee.department")
public class DepartmentExceptionController extends ExceptionController{

//    @ExceptionHandler(LowDepAlreadyExistException.class)
//    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
//    public ExceptionResponse lowDepAlreadyExist(LowDepAlreadyExistException exception) {
//        return getExceptionResponse(exception);
//    }
}
