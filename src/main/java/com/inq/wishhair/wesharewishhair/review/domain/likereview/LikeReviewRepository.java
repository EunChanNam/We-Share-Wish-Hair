package com.inq.wishhair.wesharewishhair.review.domain.likereview;

import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeReviewRepository extends JpaRepository<LikeReview, Long> {
    Optional<LikeReview> findByUserAndReview(User user, Review review);
}