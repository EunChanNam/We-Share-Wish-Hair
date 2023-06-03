package com.inq.wishhair.wesharewishhair.review.controller;

import com.inq.wishhair.wesharewishhair.auth.config.resolver.ExtractPayload;
import com.inq.wishhair.wesharewishhair.global.dto.response.Success;
import com.inq.wishhair.wesharewishhair.review.controller.dto.request.ReviewUpdateRequest;
import com.inq.wishhair.wesharewishhair.review.service.ReviewService;
import com.inq.wishhair.wesharewishhair.review.controller.dto.request.ReviewCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
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

    @DeleteMapping(path = "{reviewId}")
    public ResponseEntity<Success> deleteReview(@ExtractPayload Long userId,
                                                @PathVariable Long reviewId) {

        reviewService.deleteReview(reviewId, userId);

        return ResponseEntity.ok(new Success());
    }

    @PatchMapping
    public ResponseEntity<Success> updateReview(@ModelAttribute ReviewUpdateRequest request,
                                                @ExtractPayload Long userId) {

        reviewService.updateReview(request, userId);

        return ResponseEntity.ok(new Success());
    }
}
