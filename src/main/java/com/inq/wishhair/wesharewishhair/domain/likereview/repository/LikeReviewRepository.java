package com.inq.wishhair.wesharewishhair.domain.likereview.repository;

import com.inq.wishhair.wesharewishhair.domain.likereview.LikeReview;
import com.inq.wishhair.wesharewishhair.domain.review.Review;
import com.inq.wishhair.wesharewishhair.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeReviewRepository extends JpaRepository<LikeReview, Long> {
    Optional<LikeReview> findByUserAndReview(User user, Review review);
}