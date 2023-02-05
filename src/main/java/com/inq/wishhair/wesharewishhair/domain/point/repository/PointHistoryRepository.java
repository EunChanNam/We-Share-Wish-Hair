package com.inq.wishhair.wesharewishhair.domain.point.repository;

import com.inq.wishhair.wesharewishhair.domain.point.PointHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {

    @Query("select p from PointHistory p " +
            "where p.user.id = :userId " +
            "order by p.createdDate desc")
    Optional<PointHistory> findRecentPointByUserId(@Param("userId") Long userId,
                                                   Pageable pageable);
}
