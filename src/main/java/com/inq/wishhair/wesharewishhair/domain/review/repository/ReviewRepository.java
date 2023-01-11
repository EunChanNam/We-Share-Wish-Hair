package com.inq.wishhair.wesharewishhair.domain.review.repository;

import com.inq.wishhair.wesharewishhair.domain.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
