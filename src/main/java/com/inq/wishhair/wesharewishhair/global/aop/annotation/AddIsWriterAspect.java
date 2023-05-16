package com.inq.wishhair.wesharewishhair.global.aop.annotation;

import com.inq.wishhair.wesharewishhair.global.dto.response.PagedResponse;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewDetailResponse;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AddIsWriterAspect {

    @Pointcut("execution(com.inq.wishhair.wesharewishhair.global.dto.response.PagedResponse *(..))")
    public void pagedResponsePointcut() {}

    @Pointcut("execution(com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewDetailResponse *(..))")
    public void reviewDetailResponsePointcut() {}

    @SuppressWarnings("unchecked")
    @Around("pagedResponsePointcut() && @annotation(com.inq.wishhair.wesharewishhair.global.aop.annotation.AddisWriter) " +
            "&& args(userId, ..)")
    public Object addIsWriterToPagedResponse(ProceedingJoinPoint joinPoint, Long userId) throws Throwable {
        PagedResponse<?> result = (PagedResponse<?>) joinPoint.proceed();
        if (!(result.getResult() instanceof ReviewResponse)) {
            throw new WishHairException(ErrorCode.AOP_GENERIC_EXCEPTION);
        }
        PagedResponse<ReviewResponse> castedResult = (PagedResponse<ReviewResponse>) result;
        castedResult.getResult().forEach((response -> response.addIsWriter(userId)));
        return castedResult;
    }

    @Around("reviewDetailResponsePointcut() && @annotation(com.inq.wishhair.wesharewishhair.global.aop.annotation.AddisWriter) " +
            "&& args(userId, ..)")
    public Object addIsWriterToReviewDetailResponse(ProceedingJoinPoint joinPoint, Long userId) throws Throwable {
        ReviewDetailResponse result = (ReviewDetailResponse) joinPoint.proceed();

        result.getReviewResponse().addIsWriter(userId);
        return result;
    }
}
