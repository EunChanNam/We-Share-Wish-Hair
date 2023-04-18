package com.inq.wishhair.wesharewishhair.review.domain;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    //review find service - 리뷰 단순 조회
    @EntityGraph(attributePaths = "user")
    Optional<Review> findById(Long aLong);

    //likeReview Service
    @Lock(LockModeType.PESSIMISTIC_WRITE) //비관적 락
    Optional<Review> findWithLockById(Long id);
}
