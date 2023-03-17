package com.inq.wishhair.wesharewishhair.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    NOT_EXIST_KEY("NOT FOUND", "존재하지 않는 키 입니다.", HttpStatus.NOT_FOUND),
    LOGIN_FAIL("BAD_REQUEST", "아이디 혹은 비밀번호가 틀렸습니다.", HttpStatus.BAD_REQUEST),
    FILE_TRANSFER_EX("INTERNAL_SERVER_ERROR", "파일 저장 오류", HttpStatus.INTERNAL_SERVER_ERROR),
    EMPTY_FILE_EX("BAD_REQUEST", "빈 파일 입니다.", HttpStatus.BAD_REQUEST),
    AUTH_EXPIRED_TOKEN("AUTH_001", "토큰의 유효기간이 만료되었습니다.", HttpStatus.UNAUTHORIZED),
    AUTH_INVALID_TOKEN("AUTH_002", "토큰이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED),
    AUTH_REQUIRED_LOGIN("AUTH_003", "로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
