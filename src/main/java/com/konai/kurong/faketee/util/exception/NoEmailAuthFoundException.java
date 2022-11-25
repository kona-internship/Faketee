package com.konai.kurong.faketee.util.exception;

public class NoEmailAuthFoundException extends RuntimeException {
    public NoEmailAuthFoundException() {

        super("이메일 인증 정보를 찾지 못하였습니다.");
    }

    public NoEmailAuthFoundException(String message) {

        super(message);
    }
}
