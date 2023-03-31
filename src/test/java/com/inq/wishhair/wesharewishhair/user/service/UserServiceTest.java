package com.inq.wishhair.wesharewishhair.user.service;

import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.user.domain.point.PointHistory;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.fixture.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("UserServiceTest - SpringBootTest")
class UserServiceTest extends ServiceTest {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("회원가입 서비스 로직 테스트")
    void createUserTest() {
        User userA = UserFixture.A.toEntity();

        //logic
        Long resultA = userService.createUser(userA);

        assertThat(resultA).isEqualTo(userA.getId());
    }
}