package com.inq.wishhair.wesharewishhair.review.domain;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    //review find service - 리뷰 단순 조회
    @EntityGraph(attributePaths = "user")
    Optional<Review> findById(Long aLong);

    //likeReview Service
    @Query("select distinct r from Review r " +
            "left outer join fetch r.likeReviews.likeReviews")
    Optional<Review> findWithLikeReviewsById(@Param("id") Long id);
}
