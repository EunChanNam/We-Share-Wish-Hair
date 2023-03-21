package com.inq.wishhair.wesharewishhair.review.controller;

import com.inq.wishhair.wesharewishhair.review.common.ReviewSortCondition;
import com.inq.wishhair.wesharewishhair.review.controller.dto.response.PagedReviewResponse;
import com.inq.wishhair.wesharewishhair.review.service.ReviewFindService;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.inq.wishhair.wesharewishhair.review.common.ReviewSortCondition.LIKES;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewFindController {

    private final ReviewFindService reviewFindService;

    @GetMapping("/review")
    public ResponseEntity<PagedReviewResponse> getReviews(
            @PageableDefault(sort = LIKES) Pageable pageable,
            @RequestParam String condition) {

        List<ReviewResponse> result = reviewFindService.getReviews(pageable, condition);

        return ResponseEntity.ok(toPagedResponse(result));
    }

    private PagedReviewResponse toPagedResponse(List<ReviewResponse> result) {
        return PagedReviewResponse.of(result);
    }
}
