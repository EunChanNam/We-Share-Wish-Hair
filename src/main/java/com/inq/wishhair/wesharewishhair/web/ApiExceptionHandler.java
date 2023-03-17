package com.inq.wishhair.wesharewishhair.web;

import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorResponse;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.web.auth.AuthController;
import com.inq.wishhair.wesharewishhair.web.hairstyle.HairStyleController;
import com.inq.wishhair.wesharewishhair.web.review.ReviewController;
import com.inq.wishhair.wesharewishhair.web.user.UserController;
import com.inq.wishhair.wesharewishhair.web.wishlist.WishListController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {
        UserController.class, AuthController.class, HairStyleController.class,
        ReviewController.class, WishListController.class, AuthController.class
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
