package com.inq.wishhair.wesharewishhair.web.review;

import com.inq.wishhair.wesharewishhair.common.consts.SessionConst;
import com.inq.wishhair.wesharewishhair.domain.login.dto.UserSessionDto;
import com.inq.wishhair.wesharewishhair.domain.review.Review;
import com.inq.wishhair.wesharewishhair.domain.review.repository.ReviewRepository;
import com.inq.wishhair.wesharewishhair.domain.review.service.ReviewService;
import com.inq.wishhair.wesharewishhair.web.review.dto.request.ReviewRequest;
import com.inq.wishhair.wesharewishhair.web.review.dto.response.ReviewsResponse;
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
    private final ReviewRepository reviewRepository;

    @PostMapping("/review")
    public ResponseEntity<Void> createReview(
            @ModelAttribute ReviewRequest reviewRequest,
            @SessionAttribute(SessionConst.LONGIN_MEMBER) UserSessionDto sessionDto) {

        Long reviewId = reviewService.createReview(reviewRequest.toReviewCreateDto(sessionDto.getId()));
        return ResponseEntity
                .created(URI.create("/api/review/" + reviewId))
                .build();
    }

    @PostMapping("/review/like/{reviewId}")
    public ResponseEntity<Void> likeReview(
            @PathVariable Long reviewId,
            @SessionAttribute(SessionConst.LONGIN_MEMBER) UserSessionDto sessionDto) {

        reviewService.LikeReview(reviewId, sessionDto.getId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/review")
    public ResponseEntity<List<ReviewsResponse>> getReviews(Pageable pageable) {

        List<Review> reviews = reviewRepository.findReviewByPaging(pageable);
        List<ReviewsResponse> result = reviews.stream()
                .map(ReviewsResponse::new)
                .toList();

        return ResponseEntity.ok(result);
    }
}
