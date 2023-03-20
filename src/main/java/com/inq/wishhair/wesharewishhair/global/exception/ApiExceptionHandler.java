package com.inq.wishhair.wesharewishhair.global.exception;

import com.inq.wishhair.wesharewishhair.auth.controller.TokenReissueController;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorResponse;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.auth.controller.AuthController;
import com.inq.wishhair.wesharewishhair.hairstyle.controller.HairStyleController;
import com.inq.wishhair.wesharewishhair.review.controller.ReviewController;
import com.inq.wishhair.wesharewishhair.user.controller.UserController;
import com.inq.wishhair.wesharewishhair.wishlist.controller.WishListController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {
        UserController.class, AuthController.class, HairStyleController.class,
        ReviewController.class, WishListController.class, AuthController.class,
        TokenReissueController.class
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
