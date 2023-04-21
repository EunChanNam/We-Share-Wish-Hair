package com.inq.wishhair.wesharewishhair.review.controller;

import com.inq.wishhair.wesharewishhair.auth.config.resolver.ExtractPayload;
import com.inq.wishhair.wesharewishhair.review.service.ReviewFindService;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewFindController {

    private final ReviewFindService reviewFindService;

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> findReview(@PathVariable Long reviewId,
                                                     @ExtractPayload Long userId) {

        ReviewResponse result = reviewFindService.findReviewById(reviewId, userId);

        return ResponseEntity.ok(result);
    }
}
