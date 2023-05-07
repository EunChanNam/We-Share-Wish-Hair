package com.inq.wishhair.wesharewishhair.review.domain.likereview;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikeReviewRepository extends JpaRepository<LikeReview, Long> {

    @Modifying
    @Query("delete from LikeReview l where l.reviewId = :reviewId")
    void deleteAllByReview(@Param("reviewId") Long reviewId);

    @Modifying
    @Query("delete from LikeReview l " +
            "where l.userId = :userId and l.reviewId = :reviewId")
    void deleteByUserIdAndReviewId(@Param("userId") Long userId,
                                   @Param("reviewId") Long reviewId);

    boolean existsByUserIdAndReviewId(Long userId, Long reviewId);

    @Modifying
    @Query("delete from LikeReview l where l.reviewId in :reviewIds")
    void deleteAllByReviews(@Param("reviewIds") List<Long> reviewIds);
}
