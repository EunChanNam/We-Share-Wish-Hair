package com.inq.wishhair.wesharewishhair.likereview.repository;

import com.inq.wishhair.wesharewishhair.likereview.LikeReview;
import com.inq.wishhair.wesharewishhair.review.Review;
import com.inq.wishhair.wesharewishhair.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeReviewRepository extends JpaRepository<LikeReview, Long> {
    Optional<LikeReview> findByUserAndReview(User user, Review review);
}