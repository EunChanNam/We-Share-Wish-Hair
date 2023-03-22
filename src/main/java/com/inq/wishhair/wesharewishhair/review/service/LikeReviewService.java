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

    @Transactional
    public void LikeReview(Long reviewId, Long userId) {
        User user = findUser(userId);
        Review review = findReview(reviewId);

        executeLike(user, review);
    }

    private Review findReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new WishHairException(ErrorCode.NOT_EXIST_KEY));
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new WishHairException(ErrorCode.NOT_EXIST_KEY));
    }

    private void executeLike(User user, Review review) {
        review.getLikeReviews().executeLike(user, review);
    }
}
