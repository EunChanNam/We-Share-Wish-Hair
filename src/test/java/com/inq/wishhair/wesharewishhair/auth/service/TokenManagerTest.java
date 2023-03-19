package com.inq.wishhair.wesharewishhair.auth.service;

import com.inq.wishhair.wesharewishhair.auth.domain.Token;
import com.inq.wishhair.wesharewishhair.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("TokenManagerTest - SpringBootTest")
public class TokenManagerTest extends ServiceTest {

    @Autowired
    private TokenManager tokenManager;

    private User user;

    @BeforeEach
    void setUp() {
        //given
        user = UserFixture.A.toEntity();
        userRepository.save(user);
    }

    @Nested
    @DisplayName("RefreshToken 동기화")
    class synchronizeRefreshToken {
        @Test
        @DisplayName("user 에게 발급된 토큰이 없으면 새로 발급한 토큰을 저장한다")
        void test1() {
            //given
            String refreshToken = provider.createRefreshToken(user.getId());

            //when
            tokenManager.synchronizeRefreshToken(user, refreshToken);

            //then
            Optional<Token> result = tokenRepository.findByUser(user);
            assertThat(result).isPresent();
        }

        @Test
        @DisplayName("user 에게 발급된 토큰이 있으면 기존 토큰을 업데이트한다.")
        void test2() {
            //given
            String refreshToken = provider.createRefreshToken(user.getId());
            tokenRepository.save(Token.issue(user, refreshToken));
            String newRefreshToken = refreshToken + "diff";

            //when
            tokenManager.synchronizeRefreshToken(user, newRefreshToken);

            //then
            Optional<Token> result = tokenRepository.findByUser(user);
            assertAll(
                    () -> assertThat(result).isPresent(),
                    () -> {
                        Token token = result.get();
                        assertThat(token.getRefreshToken()).isEqualTo(newRefreshToken);
                        assertThat(token.getRefreshToken()).isNotEqualTo(refreshToken);
                    }
            );
        }
    }
}