package com.konai.kurong.faketee.employee.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 권한 확인이 필요한 메서드에 붙여주는 어노테이션.
 *
 * 속성 목록:
 * 1. role: 권한 (최고관리자, 총괄관리자, 조직관리자, 직원)
 * 2. onlyLowDep: 조직 관리자일 경우 자신의 하위 조직에 대한 요청만 허용할 것인지 여부
 *                하위 조직을 허용하지 않는다면 요청에서 DepIdsDto를 상속받는 Dto가 있어야 함
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EmpAuth {
    EmpRole role() default EmpRole.EMPLOYEE;

    boolean onlyLowDep() default true;
}
