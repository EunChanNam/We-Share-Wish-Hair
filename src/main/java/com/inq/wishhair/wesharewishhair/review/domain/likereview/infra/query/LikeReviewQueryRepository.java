package com.inq.wishhair.wesharewishhair.review.domain.likereview.infra.query;

public interface LikeReviewQueryRepository {

    boolean existByUserAndReview(Long userId, Long reviewId);
}
