package com.konai.kurong.faketee.draft.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum DraftStateCode {
    WAIT("대기중"),
    APVL_1("1차승인"),
    APVL_FIN("최종승인"),
    REJ_1("1차반려"),
    REJ_FIN("최종반려"),
    NOT_VALID("비활성화");

    private final String stateMessage;

    public static List<DraftStateCode> getWaitingForApproval(){
        return Arrays.asList(DraftStateCode.WAIT, DraftStateCode.APVL_1);
    }

    public static List<DraftStateCode> getDoneApproval(){
        return Arrays.asList(DraftStateCode.APVL_FIN, DraftStateCode.REJ_1, DraftStateCode.REJ_FIN);
    }
}
