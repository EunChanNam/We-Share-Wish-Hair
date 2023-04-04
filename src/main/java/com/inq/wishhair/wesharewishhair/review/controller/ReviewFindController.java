package com.inq.wishhair.wesharewishhair.review.controller;

import com.inq.wishhair.wesharewishhair.auth.config.resolver.ExtractPayload;
import com.inq.wishhair.wesharewishhair.global.dto.response.SimpleResponseWrapper;
import com.inq.wishhair.wesharewishhair.review.controller.dto.response.PagedReviewResponse;
import com.inq.wishhair.wesharewishhair.review.service.ReviewFindService;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewResponse;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewSimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
public class ReviewFindController {

    private final ReviewFindService reviewFindService;

    @GetMapping
    public ResponseEntity<PagedReviewResponse> findPagingReviews(
            @PageableDefault(sort = LIKES, direction = Sort.Direction.DESC) Pageable pageable) {

        Slice<ReviewResponse> result = reviewFindService.findPagedReviews(pageable);

        return ResponseEntity.ok(toPagedResponse(result));
    }

    @GetMapping("/my")
    public ResponseEntity<PagedReviewResponse> findMyReviews(
            @PageableDefault(sort = DATE, direction = Sort.Direction.ASC) Pageable pageable,
            @ExtractPayload Long userId) {

        Slice<ReviewResponse> result = reviewFindService.findMyReviews(userId, pageable);

        return ResponseEntity.ok(toPagedResponse(result));
    }

    @GetMapping("/month")
    public SimpleResponseWrapper<List<ReviewSimpleResponse>> findReviewOfMonth() {

        List<ReviewSimpleResponse> result = reviewFindService.findReviewOfMonth();

        return wrapSimpleResponse(result);
    }

    private PagedReviewResponse toPagedResponse(Slice<ReviewResponse> result) {
        return new PagedReviewResponse(result);
    }

    private <T> SimpleResponseWrapper<T> wrapSimpleResponse(T result) {
        return new SimpleResponseWrapper<>(result);
    }
}
