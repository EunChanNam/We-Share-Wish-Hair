package com.inq.wishhair.wesharewishhair.web;

import com.inq.wishhair.wesharewishhair.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.exception.ErrorResponse;
import com.inq.wishhair.wesharewishhair.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.web.user.UserController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {
        UserController.class
})
public class ApiExceptionHandler {

    @ExceptionHandler(WishHairException.class)
    public ResponseEntity<ErrorResponse> handleException(WishHairException e) {

        ErrorCode errorCode = e.getErrorCode();

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(new ErrorResponse(errorCode));
    }
}
