package com.inq.wishhair.wesharewishhair.auth.service;

import com.inq.wishhair.wesharewishhair.auth.domain.Token;
import com.inq.wishhair.wesharewishhair.auth.service.dto.response.TokenResponse;
import com.inq.wishhair.wesharewishhair.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("AuthServiceTest - SpringBootTest")
public class AuthServiceTest extends ServiceTest {

    @Autowired
    private AuthService authService;

    private final User user = UserFixture.A.toEntity();

    @BeforeEach
    void setUp() {
        //given
        userRepository.save(user);
    }

    @Nested
    @DisplayName("로그인을 한다")
    class Login {
        @Test
        @DisplayName("로그인에 성공한다")
        void test1() {
            //when
            TokenResponse result = authService.login(user.getEmailValue(), user.getPasswordValue());

            //then
            assertAll(
                    () -> assertThat(result).isNotNull(),
                    () -> assertThat(result.getAccessToken()).isNotNull(),
                    () -> assertThat(result.getRefreshToken()).isNotNull()
            );
        }

        @Test
        @DisplayName("잘못된 아이디로 로그인에 실패한다")
        void test2() {
            //when, then
            assertThatThrownBy(() -> authService.login(user.getEmailValue() + "fail", user.getPasswordValue()))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(ErrorCode.LOGIN_FAIL.getMessage());
        }

        @Test
        @DisplayName("잘못된 아이디로 로그인에 실패한다")
        void test3() {
            //when, then
            assertThatThrownBy(() -> authService.login(user.getEmailValue(), user.getPasswordValue() + "fail"))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(ErrorCode.LOGIN_FAIL.getMessage());
        }
    }

    @Test
    @DisplayName("로그아웃을 한다")
    void test4() {
        //given
        authService.login(user.getEmailValue(), user.getPasswordValue());

        //when
        authService.logout(user.getId());

        //then
        Optional<Token> result = tokenRepository.findByUser(user);
        assertThat(result).isNotPresent();
    }
}
