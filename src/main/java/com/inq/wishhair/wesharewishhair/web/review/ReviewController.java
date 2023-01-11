package com.inq.wishhair.wesharewishhair.web.review;

import com.inq.wishhair.wesharewishhair.common.consts.SessionConst;
import com.inq.wishhair.wesharewishhair.domain.login.dto.UserSessionDto;
import com.inq.wishhair.wesharewishhair.domain.review.service.ReviewService;
import com.inq.wishhair.wesharewishhair.web.review.dto.request.ReviewRequest;
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
            @SessionAttribute(SessionConst.LONGIN_MEMBER) UserSessionDto sessionDto) {

        Long reviewId = reviewService.createReview(reviewRequest.toReviewCreateDto(sessionDto.getId()));
        return ResponseEntity
                .created(URI.create("/api/review/" + reviewId))
                .build();
    }
}
