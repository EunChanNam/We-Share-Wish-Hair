package com.inq.wishhair.wesharewishhair.review.controller;

import com.inq.wishhair.wesharewishhair.auth.config.resolver.ExtractPayload;
import com.inq.wishhair.wesharewishhair.global.dto.response.PagedResponse;
import com.inq.wishhair.wesharewishhair.global.dto.response.ResponseWrapper;
import com.inq.wishhair.wesharewishhair.review.service.ReviewSearchService;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewDetailResponse;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewResponse;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewSimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.inq.wishhair.wesharewishhair.review.common.ReviewSortCondition.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewSearchController {

    private final ReviewSearchService reviewSearchService;

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDetailResponse> findReview(@PathVariable Long reviewId,
                                                           @ExtractPayload Long userId) {

        ReviewDetailResponse result = reviewSearchService.findReviewById(reviewId, userId);

        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<PagedResponse<ReviewResponse>> findPagingReviews(
            @PageableDefault(sort = LIKES, direction = Sort.Direction.DESC) Pageable pageable,
            @ExtractPayload Long userId) {

        PagedResponse<ReviewResponse> result = reviewSearchService.findPagedReviews(pageable, userId);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/my")
    public ResponseEntity<PagedResponse<ReviewResponse>> findMyReviews(
            @PageableDefault(sort = DATE, direction = Sort.Direction.DESC) Pageable pageable,
            @ExtractPayload Long userId) {

        PagedResponse<ReviewResponse> result = reviewSearchService.findMyReviews(userId, pageable);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/month")
    public ResponseWrapper<ReviewSimpleResponse> findReviewOfMonth() {

        return reviewSearchService.findReviewOfMonth();
    }
}
