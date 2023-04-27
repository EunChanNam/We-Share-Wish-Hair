package com.inq.wishhair.wesharewishhair.review.domain;

import com.inq.wishhair.wesharewishhair.review.infra.query.ReviewQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewQueryRepository {

    //review find service - 리뷰 단순 조회
    @Query("select distinct r from Review r " +
            "left outer join fetch r.photos " +
            "where r.id = :id")
    Optional<Review> findWithPhotosById(@Param("id") Long id);
}
