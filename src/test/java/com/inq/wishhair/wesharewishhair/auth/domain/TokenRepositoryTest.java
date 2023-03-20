package com.inq.wishhair.wesharewishhair.auth.domain;

import com.inq.wishhair.wesharewishhair.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.global.base.RepositoryTest;
import com.inq.wishhair.wesharewishhair.global.utils.TokenUtils;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.domain.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.inq.wishhair.wesharewishhair.global.utils.TokenUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("TokenRepositoryTest - DataJpaTest")
public class TokenRepositoryTest extends RepositoryTest {

    private User user;
    private Token token;

    @BeforeEach
    void setUp() {
        //given
        user = UserFixture.A.toEntity();
        userRepository.save(user);
        token = Token.issue(user, REFRESH_TOKEN);
        tokenRepository.save(token);
    }

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    @DisplayName("토큰을 저장한다.")
    void test1() {
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

    @Test
    @DisplayName("유저의 아이디를 가진 토큰의 리프레쉬 토큰을 업데이트 한다.")
    void test5() {
        //when
        String newRefreshToken = "new_refresh_token";
        tokenRepository.updateRefreshTokenByUserId(user.getId(), newRefreshToken);

        flushPersistence();

        //then
        Token token = tokenRepository.findByUser(user).get();
        assertAll(
                () -> assertThat(token.getRefreshToken()).isEqualTo(newRefreshToken),
                () -> assertThat(token.getUser().getId()).isEqualTo(user.getId())
        );

    }

    private void flushPersistence() {
        em.flush();
        em.clear();
    }
}
