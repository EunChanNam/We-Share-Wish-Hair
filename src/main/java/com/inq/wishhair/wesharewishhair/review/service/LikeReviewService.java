package com.inq.wishhair.wesharewishhair.review.service;

import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.domain.ReviewRepository;
import com.inq.wishhair.wesharewishhair.review.domain.likereview.LikeReview;
import com.inq.wishhair.wesharewishhair.review.domain.likereview.LikeReviewRepository;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final LikeReviewRepository likeReviewRepository;

    @Transactional
    public void LikeReview(Long reviewId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new WishHairException(ErrorCode.NOT_EXIST_KEY));
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new WishHairException(ErrorCode.NOT_EXIST_KEY));

        likeReviewRepository.findByUserAndReview(user, review)
                .ifPresentOrElse((likeReview) -> {
                    likeReviewRepository.delete(likeReview);
                    review.cancelLike();
                }, () -> {
                    LikeReview likeReview = LikeReview.createLikeReview(user, review);
                    likeReviewRepository.save(likeReview);
                    review.addLike();
                });
    }
}
