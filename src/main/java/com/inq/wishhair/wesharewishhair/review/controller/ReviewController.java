package com.inq.wishhair.wesharewishhair.review.controller;

import com.inq.wishhair.wesharewishhair.auth.config.resolver.ExtractPayload;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.service.ReviewService;
import com.inq.wishhair.wesharewishhair.review.controller.dto.request.ReviewRequest;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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

    @PostMapping("/review/like/{reviewId}")
    public ResponseEntity<Void> likeReview(
            @PathVariable Long reviewId,
            @ExtractPayload Long userId) {

        reviewService.LikeReview(reviewId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/review")
    public ResponseEntity<List<ReviewResponse>> getReviews(Pageable pageable,
                                                           @RequestParam String condition) {

        List<Review> reviews = reviewService.getReviews(pageable, condition);
        List<ReviewResponse> result = reviews.stream()
                .map(ReviewResponse::new)
                .toList();

        return ResponseEntity.ok(result);
    }
}
