package com.inq.wishhair.wesharewishhair.review.domain;

import com.inq.wishhair.wesharewishhair.review.domain.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    //photos 는 batch_fetch_size 로 해결
    @Query("select r from Review r " +
            "join fetch r.hairStyle " +
            "join fetch r.user")
    List<Review> findReviewByPaging(Pageable pageable);
}
