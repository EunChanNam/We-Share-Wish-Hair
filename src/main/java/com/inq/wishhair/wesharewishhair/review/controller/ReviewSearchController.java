package com.inq.wishhair.wesharewishhair.review.controller;

import com.inq.wishhair.wesharewishhair.auth.config.resolver.ExtractPayload;
import com.inq.wishhair.wesharewishhair.global.dto.response.PagedResponse;
import com.inq.wishhair.wesharewishhair.global.dto.response.SimpleResponseWrapper;
import com.inq.wishhair.wesharewishhair.review.service.ReviewSearchService;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewResponse;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewSimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.inq.wishhair.wesharewishhair.review.common.ReviewSortCondition.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewSearchController {

    private final ReviewSearchService reviewSearchService;

    @GetMapping
    public ResponseEntity<PagedResponse<ReviewResponse>> findPagingReviews(
            @PageableDefault(sort = LIKES, direction = Sort.Direction.DESC) Pageable pageable) {

        PagedResponse<ReviewResponse> result = reviewSearchService.findPagedReviews(pageable);

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
    public SimpleResponseWrapper<List<ReviewSimpleResponse>> findReviewOfMonth() {

        List<ReviewSimpleResponse> result = reviewSearchService.findReviewOfMonth();

        return SimpleResponseWrapper.wrapResponse(result);
    }
}
