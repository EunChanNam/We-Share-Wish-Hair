package com.inq.wishhair.wesharewishhair.review.domain.likereview;

import com.inq.wishhair.wesharewishhair.review.domain.likereview.infra.query.LikeReviewQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeReviewRepository extends JpaRepository<LikeReview, Long>, LikeReviewQueryRepository {

    @Modifying
    @Query("delete from LikeReview l where l.review.id = :reviewId")
    void deleteAllByReview(@Param("reviewId") Long reviewId);

    boolean existsByUserIdAndReviewId(Long userId, Long reviewId);
}
