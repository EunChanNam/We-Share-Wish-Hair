package com.inq.wishhair.wesharewishhair.review.service;

import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.domain.likereview.LikeReviewRepository;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.service.UserFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeReviewService {

    private final ReviewFindService reviewFindService;
    private final UserFindService userFindService;
    private final LikeReviewRepository likeReviewRepository;

    @Transactional
    public void likeReview(Long reviewId, Long userId) {
        User user = userFindService.findByUserId(userId);
        Review review = reviewFindService.findWithLikeReviewsById(reviewId);

        review.executeLike(user);
    }

    public boolean checkIsLiking(Long userId, Long reviewId) {
        return likeReviewRepository.existByUserAndReview(userId, reviewId);
    }
}
