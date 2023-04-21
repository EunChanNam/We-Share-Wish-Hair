package com.inq.wishhair.wesharewishhair.review.service;

import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.domain.ReviewRepository;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewResponseAssembler.toReviewResponse;

@Service
@RequiredArgsConstructor
public class ReviewFindService {

    private final ReviewRepository reviewRepository;

    public Review findWithUserById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new WishHairException(ErrorCode.NOT_EXIST_KEY));
    }

    public Review findWithLikeReviewsById(Long id) {
        return reviewRepository.findWithLockById(id)
                .orElseThrow(() -> new WishHairException(ErrorCode.NOT_EXIST_KEY));
    }

    /*리뷰 단건 조회*/
    public ReviewResponse findReviewById(Long reviewId, Long userId) {
        Review findReview = reviewRepository.findReviewById(reviewId)
                .orElseThrow(() -> new WishHairException(ErrorCode.NOT_EXIST_KEY));

        return toReviewResponse(findReview, userId);
    }
}
