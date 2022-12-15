package com.konai.kurong.faketee.draft.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DraftCrudType {
    CREATE("생성"),
    UPDATE("수정"),
    DELETE("삭제");

    private String type;
}
