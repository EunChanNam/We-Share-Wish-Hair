package com.inq.wishhair.wesharewishhair.user.service;

import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.user.domain.point.domain.PointHistory;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.fixture.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("UserServiceTest - SpringBootTest")
class UserServiceTest extends ServiceTest {

    @Test //todo 포인트 테스트 부분을 분리 해야되는지 확인
    @DisplayName("회원가입 서비스 로직 테스트")
    void createUserTest() {
        User userA = UserFixture.A.toEntity();

        //createUser 를 실행하기 전에는 PointHistory 가 존재하지 않는다.
        assertThat(pointHistoryTestRepository.findAll()).isEmpty();

        //logic
        Long resultA = userService.createUser(userA);

        PointHistory pointHistory = findPointHistoryByUser(userA);

        //createUser 실행 후 PointHistory 가 생성된다.
        assertThat(pointHistoryTestRepository.findAll()).isNotEmpty();

        assertAll(
                () -> assertThat(resultA).isEqualTo(userA.getId()), // createUser response validate
                () -> assertThat(pointHistory.getUser().getId()).isEqualTo(userA.getId()) // pointHistory create validate
        );

    }

    private PointHistory findPointHistoryByUser(User userA) {
        return pointHistoryTestRepository.findTopByUser(userA)
                .orElseThrow(() -> new WishHairException(ErrorCode.NOT_EXIST_KEY));
    }
}