package com.inq.wishhair.wesharewishhair.review.domain;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    //review find service - 리뷰 단순 조회
    @Override
    @EntityGraph(attributePaths = "user")
    Optional<Review> findById(Long aLong);
}
