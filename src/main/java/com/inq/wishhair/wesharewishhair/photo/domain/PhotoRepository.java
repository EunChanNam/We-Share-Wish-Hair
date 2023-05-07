package com.inq.wishhair.wesharewishhair.photo.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    @Modifying
    @Query("delete from Photo p where p.review.id = :reviewId")
    void deleteAllByReview(@Param("reviewId") Long reviewId);

    @Modifying
    @Query("delete from Photo p where p.review.id in :reviewIds")
    void deleteAllByReviews(@Param("reviewIds") List<Long> reviewIds);
}
