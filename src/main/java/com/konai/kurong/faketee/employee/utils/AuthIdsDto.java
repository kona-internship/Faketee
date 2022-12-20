package com.konai.kurong.faketee.employee.utils;

/**
 * 어노테이션 @EmpAuth 의 타입(paramCheckType)에 따라 검증할 목록을 반환
 * 검증받을 목록은 EmpAuthCheckList 클래스에 래핑하여 반환한다.
 *
 * getEmpAuthCheckList(): 요청자의 하위 조직임을 검증 받을 목록 객체
 */
public interface AuthIdsDto {
    EmpAuthCheckList getEmpAuthCheckList();
}
