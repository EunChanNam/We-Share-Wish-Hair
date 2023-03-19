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

    @Test
    @DisplayName("사용자 아이디로 토큰을 삭제한다")
    void test3() {
        //given
        Long userId = user.getId();
        String refreshToken = provider.createRefreshToken(user.getId());
        tokenRepository.save(Token.issue(user, refreshToken));

        //when
        tokenManager.deleteToken(userId);

        //then
        Optional<Token> result = tokenRepository.findByUser(user);
        assertThat(result).isNotPresent();
    }

    //todo 마지막 user 쿼리 왜나가지는 모르겠음
    @Test
    @DisplayName("RTR 정책에 의해 Refresh 토큰을 업데이트한다.")
    void test4() {
        //given
        String refreshToken = provider.createRefreshToken(user.getId());
        tokenRepository.save(Token.issue(user, refreshToken));
        String newRefreshToken = refreshToken + "diff";

        //when
        tokenManager.updateRefreshTokenByRTR(user.getId(), newRefreshToken);
        flushPersistence();

        //then
        Token token = tokenRepository.findByUser(user).get();
        assertThat(token.getRefreshToken()).isEqualTo(newRefreshToken);
    }

    private void flushPersistence() {
        em.flush();
        em.clear();
    }
}