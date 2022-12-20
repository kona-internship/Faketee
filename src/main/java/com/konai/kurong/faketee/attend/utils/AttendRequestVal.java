package com.konai.kurong.faketee.attend.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AttendRequestVal {

    C("그냥, 수정 이외 다"),
    U("수정");

   private final String val;
}
