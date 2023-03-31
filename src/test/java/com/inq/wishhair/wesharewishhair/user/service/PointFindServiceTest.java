package com.inq.wishhair.wesharewishhair.user.service;

import com.inq.wishhair.wesharewishhair.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("PointFindServiceTest - SpringBootTest")
public class PointFindServiceTest extends ServiceTest {

    @Autowired
    private PointFindService pointFindService;

    @BeforeEach
    void setUp() {
        userRepository.save(UserFixture.A.toEntity());

    }

    @Test
    @DisplayName("사용자의 포인트 내역을 조회한다")
    void test1() {

    }
}
