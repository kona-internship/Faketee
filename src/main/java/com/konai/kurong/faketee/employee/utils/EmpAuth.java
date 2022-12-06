package com.konai.kurong.faketee.employee.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EmpAuth {
    EmpRole role() default EmpRole.EMPLOYEE;
}
