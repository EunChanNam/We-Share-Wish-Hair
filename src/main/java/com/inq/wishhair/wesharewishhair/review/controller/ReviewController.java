package com.inq.wishhair.wesharewishhair.review.controller;

import com.inq.wishhair.wesharewishhair.auth.config.resolver.ExtractPayload;
import com.inq.wishhair.wesharewishhair.global.dto.response.Success;
import com.inq.wishhair.wesharewishhair.review.controller.dto.request.ReviewDeleteRequest;
import com.inq.wishhair.wesharewishhair.review.service.ReviewService;
import com.inq.wishhair.wesharewishhair.review.controller.dto.request.ReviewCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Success> createReview(
            @ModelAttribute ReviewCreateRequest reviewCreateRequest,
            @ExtractPayload Long userId) {

        Long reviewId = reviewService.createReview(reviewCreateRequest, userId);
        return ResponseEntity
                .created(URI.create("/api/review/" + reviewId))
                .body(new Success());
    }

    @DeleteMapping
    public ResponseEntity<Success> deleteReview(@ExtractPayload Long userId,
                                                @RequestBody ReviewDeleteRequest request) {

        reviewService.deleteReview(request.getReviewId(), userId);

        return ResponseEntity.ok(new Success());
    }
}
