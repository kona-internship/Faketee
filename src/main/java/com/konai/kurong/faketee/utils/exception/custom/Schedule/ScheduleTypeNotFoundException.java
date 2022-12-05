package com.konai.kurong.faketee.utils.exception.custom.Schedule;

import com.konai.kurong.faketee.utils.exception.ExceptionId;
import com.konai.kurong.faketee.utils.exception.custom.CustomRuntimeException;

public class ScheduleTypeNotFoundException extends CustomRuntimeException {

    public ScheduleTypeNotFoundException(){

        super(ExceptionId.SCH_TYPE_NOT_FOUND.getExceptionId());
    }

    public ScheduleTypeNotFoundException(String msg){

        super(msg);
    }
}
