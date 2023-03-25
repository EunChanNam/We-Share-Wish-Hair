package com.inq.wishhair.wesharewishhair.review.controller;

import com.inq.wishhair.wesharewishhair.review.controller.dto.response.PagedReviewResponse;
import com.inq.wishhair.wesharewishhair.review.service.ReviewFindService;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.inq.wishhair.wesharewishhair.review.common.ReviewSortCondition.LIKES;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewFindController {

    private final ReviewFindService reviewFindService;

    @GetMapping("/review")
    public ResponseEntity<PagedReviewResponse> findPagingReviews(
            @PageableDefault(sort = LIKES, direction = Sort.Direction.DESC) Pageable pageable) {

        Slice<ReviewResponse> result = reviewFindService.findPagingReviews(pageable);

        return ResponseEntity.ok(toPagedResponse(result));
    }

    private PagedReviewResponse toPagedResponse(Slice<ReviewResponse> result) {
        return PagedReviewResponse.of(result.getContent(), result.getSize());
    }
}
