package com.inq.wishhair.wesharewishhair.common.testrepository;

import com.inq.wishhair.wesharewishhair.domain.point.PointHistory;
import com.inq.wishhair.wesharewishhair.domain.user.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PointHistoryTestRepository extends JpaRepository<PointHistory, Long> {

    @EntityGraph(attributePaths = {"user"})
    Optional<PointHistory> findTopByUser(User user);
}
