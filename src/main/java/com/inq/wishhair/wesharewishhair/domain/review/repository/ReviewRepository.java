package com.inq.wishhair.wesharewishhair.domain.review.repository;

import com.inq.wishhair.wesharewishhair.domain.review.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r from Review r " +
            "left outer join fetch r.photos " +
            "group by r.id")
    List<Review> findReviewByPaging(Pageable pageable);
}
