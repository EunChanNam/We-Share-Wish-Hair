package com.inq.wishhair.wesharewishhair.review.domain;

import com.inq.wishhair.wesharewishhair.review.infra.query.ReviewQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewQueryRepository {

    //review find service - 리뷰 단순 조회
    @Query("select distinct r from Review r " +
            "left outer join fetch r.photos " +
            "where r.id = :id")
    Optional<Review> findWithPhotosById(@Param("id") Long id);

    //회원 탈퇴를 위한 사용자가 작성한 리뷰 조회
    @Query("select distinct r from Review r " +
            "left outer join fetch r.photos " +
            "where r.writer.id = :userId")
    List<Review> findWithPhotosByUserId(@Param("userId") Long userId);

    @Modifying
    @Query("delete from Review r where r.id in :reviewIds")
    void deleteAllByWriter(@Param("reviewIds") List<Long> reviewIds);
}
