package com.konai.kurong.faketee.employee.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum AtdState {
    AT_WORK("근무중"),
    OVER_WORK("초과근무"),
    LATE("지각"),
    OFF_WORK("퇴근완료"),
    BF_WORK("출근가능"),
    ABSENCE("결근");

    private final String state;

    AtdState(String state){
        this.state = state;
    }

    public String state(){
        return state;
    }
}
