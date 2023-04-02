package com.inq.wishhair.wesharewishhair.photo.domain;

import com.inq.wishhair.wesharewishhair.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    void deleteByReview(Review review);
}
