package com.inq.wishhair.wesharewishhair.review.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    //photos 는 batch_fetch_size 로 해결
    @Query("select r from Review r " +
            "join fetch r.hairStyle " +
            "join fetch r.user")
    Slice<Review> findReviewByPaging(Pageable pageable);
}
