package com.inq.wishhair.wesharewishhair.review.service;

import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.review.domain.likereview.LikeReview;
import com.inq.wishhair.wesharewishhair.review.domain.likereview.LikeReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeReviewService {

    private final LikeReviewRepository likeReviewRepository;

    @Transactional
    public void executeLike(Long reviewId, Long userId) {
        validateIsNotLiking(userId, reviewId);

        likeReviewRepository.save(LikeReview.addLike(userId, reviewId));
    }

    @Transactional
    public void cancelLike(Long reviewId, Long userId) {
        validateIsLiking(userId, reviewId);

        likeReviewRepository.deleteByUserIdAndReviewId(userId, reviewId);
    }

    public boolean checkIsLiking(Long userId, Long reviewId) {
        return likeReviewRepository.existsByUserIdAndReviewId(userId, reviewId);
    }

    private void validateIsNotLiking(Long userId, Long reviewId) {
        if (checkIsLiking(userId, reviewId)) {
            throw new WishHairException(ErrorCode.REVIEW_ALREADY_LIKING);
        }
    }

    private void validateIsLiking(Long userId, Long reviewId) {
        if (!checkIsLiking(userId, reviewId)) {
            throw new WishHairException(ErrorCode.REVIEW_NOT_LIKING);
        }
    }
}
