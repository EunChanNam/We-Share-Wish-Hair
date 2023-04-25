package com.inq.wishhair.wesharewishhair.global.exception;

import com.inq.wishhair.wesharewishhair.auth.controller.MailAuthController;
import com.inq.wishhair.wesharewishhair.auth.controller.TokenReissueController;
import com.inq.wishhair.wesharewishhair.auth.controller.AuthController;
import com.inq.wishhair.wesharewishhair.hairstyle.controller.HairStyleSearchController;
import com.inq.wishhair.wesharewishhair.review.controller.LikeReviewController;
import com.inq.wishhair.wesharewishhair.review.controller.ReviewController;
import com.inq.wishhair.wesharewishhair.review.controller.ReviewSearchController;
import com.inq.wishhair.wesharewishhair.user.controller.*;
import com.inq.wishhair.wesharewishhair.hairstyle.controller.WishHairController;
import com.inq.wishhair.wesharewishhair.hairstyle.controller.WishHairSearchController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice(assignableTypes = {
        UserController.class, AuthController.class, HairStyleSearchController.class,
        ReviewController.class, WishHairController.class, AuthController.class,
        TokenReissueController.class, MailAuthController.class, UserInfoController.class,
        LikeReviewController.class, ReviewSearchController.class, PointSearchController.class,
        PointController.class, WishHairSearchController.class
})
public class ApiExceptionHandler {

    @ExceptionHandler(WishHairException.class)
    public ResponseEntity<ErrorResponse> handleWishHairException(WishHairException e) {
        return convert(e.getErrorCode());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException() {
        return convert(ErrorCode.GLOBAL_VALIDATION_ERROR);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handelNotSupportedUriException() {
        return convert(ErrorCode.GLOBAL_NOT_SUPPORTED_URI);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handlerHttpRequestMethodNotSupportedException() {
        return convert(ErrorCode.GLOBAL_NOT_SUPPORTED_METHOD);
    }

//    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleAnyException() {
        return convert(ErrorCode.GLOBAL_INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> convert(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus()).body(new ErrorResponse(errorCode));
    }


}
