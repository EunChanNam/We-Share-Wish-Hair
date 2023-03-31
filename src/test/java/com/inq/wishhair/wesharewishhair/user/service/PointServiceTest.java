package com.inq.wishhair.wesharewishhair.user.service;

import com.inq.wishhair.wesharewishhair.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("PointServiceTest - SpringBootTest")
public class PointServiceTest extends ServiceTest {

    @Autowired
    private PointService pointService;

    private User user;

    @BeforeEach
    void setUp() {
        //given
        user = UserFixture.A.toEntity();
        userRepository.save(user);
    }

    @Nested
    @DisplayName("포인트 충전 서비스")
    class chargePoint {
        @Test
        @DisplayName("올바르지 않은 dealAmount 로 충전에 실패한다")
        void test1() {
            //given
            ErrorCode expectedError = ErrorCode.POINT_INVALID_POINT_RANGE;

            //when, then
            assertThatThrownBy(() -> pointService.chargePoint(-1000, user.getId()))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(expectedError.getMessage());
        }

        @Test
        @DisplayName("포인트 충전에 성공한다")
        void test2() {
            //when
            pointService.chargePoint(1000, user.getId());

            //then
            int availablePoint = user.getAvailablePoint();
            assertThat(availablePoint).isEqualTo(1000);
        }
    }



}
