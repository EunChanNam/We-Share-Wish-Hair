package com.inq.wishhair.wesharewishhair.auth.domain;

import com.inq.wishhair.wesharewishhair.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.global.base.RepositoryTest;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("TokenRepositoryTest - DataJpaTest")
public class TokenRepositoryTest extends RepositoryTest {

    private static final String REFRESH_TOKEN = "refresh_token";
    private User user;

    @BeforeEach
    void setUp() {
        //given
        user = UserFixture.A.toEntity();
        userRepository.save(user);
        Token token = Token.issue(user, REFRESH_TOKEN);
        tokenRepository.save(token);
    }

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("토큰을 저장한다.")
    void test1() {
        //given
        User user2 = UserFixture.B.toEntity();
        userRepository.save(user2);
        Token token = Token.issue(user2, "SAVE_TEST");
        tokenRepository.save(token);

        //when
        Optional<Token> result = tokenRepository.findById(token.getId());

        //then
        assertThat(result).isPresent();
    }

    @Test
    @DisplayName("User 로 토큰을 조회한다.")
    void test2() {
        //when
        Optional<Token> result = tokenRepository.findByUser(user);

        //then
        assertAll(
                () -> assertThat(result).isPresent(),
                () -> {
                    Token token = result.get();
                    assertThat(token.getUser()).isEqualTo(user);
                    assertThat(token.getRefreshToken()).isEqualTo(REFRESH_TOKEN);
                }
        );
    }

    @Test
    @DisplayName("유저의 아이디와 리프레쉬 토큰으로 토큰을 조회한다.")
    void test3() {
        //when
        Optional<Token> result = tokenRepository.findByUserIdAndRefreshToken(user.getId(), REFRESH_TOKEN);

        //then
        assertAll(
                () -> assertThat(result).isPresent(),
                () -> {
                    Token token = result.get();
                    assertThat(token.getUser()).isEqualTo(user);
                    assertThat(token.getRefreshToken()).isEqualTo(REFRESH_TOKEN);
                }
        );
    }

    @Test
    @DisplayName("유저의 아이디로 토큰을 삭제한다.")
    void test4() {
        //when
        tokenRepository.deleteByUserId(user.getId());

        //then
        Optional<Token> token = tokenRepository.findByUser(user);
        assertThat(token).isNotPresent();
    }
}
