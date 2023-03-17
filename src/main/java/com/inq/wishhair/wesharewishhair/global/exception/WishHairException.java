package com.inq.wishhair.wesharewishhair.global.exception;

import lombok.Getter;

@Getter
public class WishHairException extends RuntimeException {

    private final ErrorCode errorCode;

    public WishHairException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
