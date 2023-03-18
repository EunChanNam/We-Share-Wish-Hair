package com.inq.wishhair.wesharewishhair.global.testrepository;


import com.inq.wishhair.wesharewishhair.user.domain.point.PointHistory;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.domain.UserRepository;
import com.inq.wishhair.wesharewishhair.fixture.UserFixture;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PointHistoryTestRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager em;

    private PointHistoryTestRepository repository;

    @BeforeEach
    void init() {
        this.repository = new PointHistoryTestRepository(em);
    }

    @Test
    @DisplayName("테스트용 포인트 리포지토리를 테스트 한다 - save(), findTopByUser()")
    void test() {
        //given
        User user = UserFixture.A.toEntity();
        userRepository.save(user);
        PointHistory pointHistory = PointHistory.createJoinPointHistory(user);

        //when
        repository.save(pointHistory);

        //then
        PointHistory findPoint = repository.findTopByUser(user).get();
        assertThat(findPoint.getId()).isEqualTo(pointHistory.getId());
    }
}
