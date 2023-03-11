package com.inq.wishhair.wesharewishhair.common.testrepository;


import com.inq.wishhair.wesharewishhair.domain.point.PointHistory;
import com.inq.wishhair.wesharewishhair.domain.user.User;
import com.inq.wishhair.wesharewishhair.fixture.UserFixture;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class PointHistoryTestRepositoryTest {

    private final PointHistoryTestRepository repository;

    public PointHistoryTestRepositoryTest(EntityManager em) {
        this.repository = new PointHistoryTestRepository(em);
    }

    @Test
    void saveTest() {
        //given
        User user = UserFixture.A.toEntity();
        PointHistory pointHistory = PointHistory.createJoinPointHistory(user);

        //when
        repository.save(pointHistory);

        //then

    }
}
