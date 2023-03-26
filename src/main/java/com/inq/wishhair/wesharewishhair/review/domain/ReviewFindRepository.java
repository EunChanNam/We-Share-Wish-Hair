package com.inq.wishhair.wesharewishhair.review.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReviewFindRepository extends JpaRepository<Review, Long> {

    @Query("select distinct r from Review r " +
            "join fetch r.photos " +
            "join fetch r.hairStyle " +
            "join fetch r.user")
    Slice<Review> findReviewByPaging(Pageable pageable);

    @Query("select r from Review r " +
            "join LikeReview l " +
            "on r.id = l.review.id " +
            "where l.user.id = :userId")
    Slice<Review> findReviewByLike(@Param("userId") Long userId, Pageable pageable);

    @EntityGraph(attributePaths = "likeReviews")
    Optional<Review> findDistinctById(Long id);
}
