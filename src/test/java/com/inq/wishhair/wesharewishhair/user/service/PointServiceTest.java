package com.inq.wishhair.wesharewishhair.user.service;

import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.PointUseRequest;
import com.inq.wishhair.wesharewishhair.user.controller.utils.PointUseRequestUtils;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.event.RefundMailSendEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RecordApplicationEvents
@DisplayName("PointServiceTest - SpringBootTest")
public class PointServiceTest extends ServiceTest {

    @Autowired
    private ApplicationEvents events;

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
            int availablePoint = user.getPoints();
            assertThat(availablePoint).isEqualTo(1000);
        }
    }

    @Nested
    @DisplayName("포인트 사용 서비스")
    class usePoint {
        @Test
        @DisplayName("현재 사용 가능한 포인트보다 큰 dealAmount 로 사용에 실패한다")
        void test3() {
            //given
            PointUseRequest request = PointUseRequestUtils.request(1000);
            ErrorCode expectedError = ErrorCode.POINT_NOT_ENOUGH;

            //when, then
            assertThatThrownBy(() -> pointService.usePoint(request, user.getId()))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(expectedError.getMessage());

            int count = (int) events.stream(RefundMailSendEvent.class).count();
            assertThat(count).isZero();
        }

        @Test
        @DisplayName("포인트 사용에 성공한다")
        void test4() {
            //given
            pointService.chargePoint(1000, user.getId());
            PointUseRequest request = PointUseRequestUtils.request(1000);

            //when
            pointService.usePoint(request, user.getId());

            //then
            int availablePoint = user.getPoints();
            assertThat(availablePoint).isZero();

            int count = (int) events.stream(RefundMailSendEvent.class).count();
            assertThat(count).isEqualTo(1);
        }
    }
}
