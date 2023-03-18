package com.inq.wishhair.wesharewishhair.review.controller;

import com.inq.wishhair.wesharewishhair.auth.config.resolver.ExtractPayload;
import com.inq.wishhair.wesharewishhair.review.service.ReviewService;
import com.inq.wishhair.wesharewishhair.review.controller.dto.request.ReviewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/review")
    public ResponseEntity<Void> createReview(
            @ModelAttribute ReviewRequest reviewRequest,
            @ExtractPayload Long userId) {

        Long reviewId = reviewService.createReview(reviewRequest.toReviewCreateDto(userId));
        return ResponseEntity
                .created(URI.create("/api/review/" + reviewId))
                .build();
    }
}
