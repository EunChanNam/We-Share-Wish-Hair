package com.inq.wishhair.wesharewishhair.user.domain.point;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.geo.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface PointFindRepository extends JpaRepository<Point, Long> {

    @Query("select p from PointHistory p " +
            "where p.user.id = :userId " +
            "order by p.createdDate desc")
    Slice<PointHistory> findByUserIdOrderByNew(@Param("userId") Long userId,
                                               Pageable pageable);
}
