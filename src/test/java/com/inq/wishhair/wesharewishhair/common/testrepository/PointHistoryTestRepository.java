package com.inq.wishhair.wesharewishhair.common.testrepository;

import com.inq.wishhair.wesharewishhair.domain.point.PointHistory;
import com.inq.wishhair.wesharewishhair.domain.user.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class PointHistoryTestRepository {

    private final EntityManager em;

    public Optional<PointHistory> findTopByUser(User user) {
        String query = "select p from PointHistory p " +
                "join fetch p.user " +
                "where p.user = :user";
        List<PointHistory> result = em.createQuery(query, PointHistory.class)
                .setParameter("user", user)
                .getResultList();
        if (result.isEmpty()) return Optional.empty();
        return Optional.ofNullable(result.get(0));
    }

    public void save(PointHistory pointHistory) {
        em.persist(pointHistory);
    }

    public List<PointHistory> findAll() {
        return em.createQuery("select p from PointHistory p", PointHistory.class)
                .getResultList();
    }
}
