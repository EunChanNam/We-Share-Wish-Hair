package com.inq.wishhair.wesharewishhair.review.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReviewFindRepository extends JpaRepository<Review, Long> {

    //review find service - 전체 리뷰 조회
    @Query("select distinct r from Review r " +
            "join fetch r.photos " +
            "join fetch r.hairStyle " +
            "join fetch r.user")
    Slice<Review> findReviewByPaging(Pageable pageable);

    //review find service - 좋아요한 리뷰 조회
    @Query("select r from Review r " +
            "join LikeReview l " +
            "on r.id = l.review.id " +
            "join fetch r.user " +
            "join fetch r.hairStyle " +
            "where l.user.id = :userId")
    Slice<Review> findReviewByLike(@Param("userId") Long userId, Pageable pageable);

    //review find service
    @Query("select distinct r from Review r " +
            "join fetch r.photos " +
            "join fetch r.hairStyle " +
            "join fetch r.user " +
            "where r.user.id = :userId ")
    Slice<Review> findReviewByUser(@Param("userId") Long userId, Pageable pageable);

    //review find service
    @Query("select r from Review r " +
            "join fetch r.hairStyle " +
            "join fetch r.user " +
            "where r.createdDate between :startDate and :endDate " +
            "order by r.likeReviews.likes")
    List<Review> findReviewByCreatedDate(@Param("startDate") LocalDate startDate,
                                         @Param("endDate") LocalDate endDate,
                                         Pageable pageable);

    //likeReview Service
    @EntityGraph(attributePaths = "likeReviews")
    Optional<Review> findDistinctById(Long id);
}
