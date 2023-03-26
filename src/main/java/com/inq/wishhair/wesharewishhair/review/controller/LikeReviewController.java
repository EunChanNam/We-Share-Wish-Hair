package com.inq.wishhair.wesharewishhair.review.controller;

import com.inq.wishhair.wesharewishhair.auth.config.resolver.ExtractPayload;
import com.inq.wishhair.wesharewishhair.global.dto.response.Success;
import com.inq.wishhair.wesharewishhair.review.service.LikeReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikeReviewController {

    private final LikeReviewService likeReviewService;

    @PostMapping("/review/like/{reviewId}")
    public ResponseEntity<Success> likeReview(
            @PathVariable Long reviewId,
            @ExtractPayload Long userId) {

        likeReviewService.LikeReview(reviewId, userId);
        return ResponseEntity.ok(new Success());
    }
}
