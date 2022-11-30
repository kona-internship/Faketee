package com.konai.kurong.faketee.account.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Type {

    GENERAL("TYPE_GENERAL", "일반"),
    GOOGLE("TYPE_GOOGLE", "구글"),
    NAVER("TYPE_NAVER", "네이버");

    private final String key;
    private final String title;
}
