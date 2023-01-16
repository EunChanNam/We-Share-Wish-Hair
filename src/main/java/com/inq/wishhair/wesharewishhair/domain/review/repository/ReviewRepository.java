package com.inq.wishhair.wesharewishhair.domain.review.repository;

import com.inq.wishhair.wesharewishhair.domain.review.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    //photos 는 fetch join, likeReviews 는 batch_fetch_size 로 해결
    @Query("select distinct r from Review r " +
            "left outer join fetch r.photos " +
            "join fetch r.hairStyle " +
            "join fetch r.user")
    List<Review> findReviewByPaging(Pageable pageable);
}
