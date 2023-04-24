package com.inq.wishhair.wesharewishhair.review.controller;

import com.inq.wishhair.wesharewishhair.auth.config.resolver.ExtractPayload;
import com.inq.wishhair.wesharewishhair.global.dto.response.Success;
import com.inq.wishhair.wesharewishhair.review.service.LikeReviewService;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewLikeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review/like/")
public class LikeReviewController {

    private final LikeReviewService likeReviewService;

    @PostMapping("/{reviewId}")
    public ResponseEntity<Success> likeReview(
            @PathVariable Long reviewId,
            @ExtractPayload Long userId) {

        likeReviewService.likeReview(reviewId, userId);
        return ResponseEntity.ok(new Success());
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewLikeResponse> checkIsLiking(@ExtractPayload Long userId,
                                                       @PathVariable Long reviewId) {

        boolean result = likeReviewService.checkIsLiking(userId, reviewId);
        return ResponseEntity.ok(new ReviewLikeResponse(result));
    }
}
