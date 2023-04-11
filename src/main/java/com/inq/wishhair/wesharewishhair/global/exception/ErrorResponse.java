package com.inq.wishhair.wesharewishhair.global.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private final String status;

    private final String code;

    private final String message;

    public ErrorResponse(ErrorCode errorCode) {
        this.status = String.valueOf(errorCode.getHttpStatus().value());
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
}
