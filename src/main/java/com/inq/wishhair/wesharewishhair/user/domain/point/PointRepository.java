package com.inq.wishhair.wesharewishhair.user.domain.point;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PointRepository extends JpaRepository<PointHistory, Long> {

    @Query("select p from PointHistory p " +
            "where p.user.id = :userId " +
            "order by p.createdDate desc")
    List<PointHistory> findRecentPointByUserId(@Param("userId") Long userId,
                                               Pageable pageable);
}
