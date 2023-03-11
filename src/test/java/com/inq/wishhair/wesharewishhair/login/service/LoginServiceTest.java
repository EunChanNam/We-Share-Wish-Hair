package com.inq.wishhair.wesharewishhair.login.service;

import com.inq.wishhair.wesharewishhair.common.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.domain.login.dto.UserSessionDto;
import com.inq.wishhair.wesharewishhair.domain.user.User;
import com.inq.wishhair.wesharewishhair.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.fixture.UserFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@DisplayName("LoginServiceTest - SpringBootTest")
public class LoginServiceTest extends ServiceTest {

    private final User user = UserFixture.A.toEntity();

    @BeforeEach
    void createUser() {
        //given
        userService.createUser(user);
    }

    @Test
    @DisplayName("로그인 성공")
    void loginSuccess() {
        //when
        UserSessionDto sessionDto = loginService.login(user.getLoginId(), user.getPw());

        //then
        assertAll(
                () -> assertThat(sessionDto).isNotNull(),
                () -> assertThat(sessionDto.getId()).isEqualTo(user.getId()),
                () -> assertThat(sessionDto.getName()).isEqualTo(user.getName())
        );
    }
}
