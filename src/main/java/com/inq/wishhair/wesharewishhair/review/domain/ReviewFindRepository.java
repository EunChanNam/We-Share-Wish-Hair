package com.inq.wishhair.wesharewishhair.review.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReviewFindRepository extends JpaRepository<Review, Long> {

    //review find service
    @Query("select distinct r from Review r " +
            "join fetch r.photos " +
            "join fetch r.hairStyle " +
            "join fetch r.user")
    Slice<Review> findReviewByPaging(Pageable pageable);

    //review find service
    @Query("select r from Review r " +
            "join LikeReview l " +
            "on r.id = l.review.id " +
            "join fetch r.user " +
            "join fetch r.hairStyle " +
            "where l.user.id = :userId")
    Slice<Review> findReviewByLike(@Param("userId") Long userId, Pageable pageable);

    //likeReview Service
    @EntityGraph(attributePaths = "likeReviews")
    Optional<Review> findDistinctById(Long id);

    //review find Service
    @Query("select distinct r from Review r " +
            "join fetch r.photos " +
            "join fetch r.hairStyle " +
            "join fetch r.user " +
            "where r.user.id = :userId ")
    Slice<Review> findReviewByUser(@Param("userId") Long userId, Pageable pageable);
}
