package com.konai.kurong.faketee.draft.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DraftRequestType {
    ATTENDANCE("출퇴근"),
    VACATION("휴가");

    private String type;
}
