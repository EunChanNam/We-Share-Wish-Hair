package com.inq.wishhair.wesharewishhair.domain.review.repository;

import com.inq.wishhair.wesharewishhair.domain.review.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    //group by 를 사용하면 데이터 뻥튀기를 막을수 있어서 다중 컬렉션 패치조인도 가능하다.
    @Query("select r from Review r " +
            "left outer join fetch r.photos " +
            "left outer join fetch r.likeReviews " +
            "group by r.id")
    List<Review> findReviewByPaging(Pageable pageable);
}
