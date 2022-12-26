package com.konai.kurong.faketee.vacation.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RequestVal {

    T("유효한 요청"),
    F("수정 요청");

    private final String val;
}

