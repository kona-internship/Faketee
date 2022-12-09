package com.konai.kurong.faketee.utils.exception.controller.empauth;

import com.konai.kurong.faketee.utils.exception.controller.ExceptionController;
import com.konai.kurong.faketee.utils.exception.custom.empauth.EmpCorDiffException;
import com.konai.kurong.faketee.utils.exception.custom.empauth.EmpDepDiffException;
import com.konai.kurong.faketee.utils.exception.custom.empauth.EmpNotPermitException;
import com.konai.kurong.faketee.utils.exception.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.konai.kurong.faketee.employee")
public class EmployeeAuthExceptionController extends ExceptionController {

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
