package com.konai.kurong.faketee.employee.utils;


import lombok.AllArgsConstructor;

import java.util.List;

/**
 * 조직관리자의 권한을 갖는 메서드에 대해서 자신의 하위조직인지 검증이 필요할 때 구현해주는 인터페이스
 *
 * getDepIds(): 요청자의 하위 조직임을 검증 받을 조직들의 리스트
 */
public interface DepIdsDto {
    List<Long> getDepIds();
}
