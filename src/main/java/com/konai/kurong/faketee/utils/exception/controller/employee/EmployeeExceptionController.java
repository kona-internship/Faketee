package com.konai.kurong.faketee.utils.exception.controller.employee;

import com.konai.kurong.faketee.utils.exception.controller.ExceptionController;
import com.konai.kurong.faketee.utils.exception.custom.employee.EmpCorDiffException;
import com.konai.kurong.faketee.utils.exception.custom.employee.EmpDepDiffException;
import com.konai.kurong.faketee.utils.exception.custom.employee.EmpNotPermitException;
import com.konai.kurong.faketee.utils.exception.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.konai.kurong.faketee.employee")
public class EmployeeExceptionController extends ExceptionController {

    @ExceptionHandler(EmpCorDiffException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ExceptionResponse empCorDiff(EmpCorDiffException exception) {
        return getExceptionResponse(exception);
    }

    @ExceptionHandler(EmpDepDiffException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ExceptionResponse empDepDiff(EmpDepDiffException exception) {
        return getExceptionResponse(exception);
    }

    @ExceptionHandler(EmpNotPermitException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ExceptionResponse empNotPermit(EmpNotPermitException exception) {
        return getExceptionResponse(exception);
    }
}
