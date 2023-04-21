package com.inq.wishhair.wesharewishhair.review.domain;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    //review find service - 리뷰 단순 조회
    @EntityGraph(attributePaths = "user")
    Optional<Review> findById(Long aLong);

    //likeReview Service
    @Lock(LockModeType.PESSIMISTIC_WRITE) //비관적 락
    Optional<Review> findWithLockById(Long id);

    //review 단건 조회
    @Query("select distinct r from Review r " +
            "left outer join fetch r.photos " +
            "join fetch r.hairStyle " +
            "join fetch r.user " +
            "where r.id = :id")
    Optional<Review> findReviewById(@Param("id") Long id);
}
