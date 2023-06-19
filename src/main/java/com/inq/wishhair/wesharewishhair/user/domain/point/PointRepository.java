package com.inq.wishhair.wesharewishhair.user.domain.point;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PointRepository extends JpaRepository<PointHistory, Long> {

    @Modifying
    @Query("delete from PointHistory p where p.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);
}
