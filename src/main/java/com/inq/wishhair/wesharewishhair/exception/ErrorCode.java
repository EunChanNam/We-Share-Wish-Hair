package com.inq.wishhair.wesharewishhair.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    NOT_EXIST_KEY("NOT FOUND", "존재하지 않는 키 입니다.", HttpStatus.NOT_FOUND),
    LOGIN_FAIL("BAD_REQUEST", "아이디 혹은 비밀번호가 틀렸습니다.", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
