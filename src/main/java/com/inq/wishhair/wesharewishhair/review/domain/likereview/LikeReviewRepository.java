package com.inq.wishhair.wesharewishhair.review.domain.likereview;

import com.inq.wishhair.wesharewishhair.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeReviewRepository extends JpaRepository<LikeReview, Long> {

    void deleteByReview(Review review);
}
