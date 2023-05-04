package com.inq.wishhair.wesharewishhair.auth.domain;

import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.inq.wishhair.wesharewishhair.global.utils.TokenUtils.REFRESH_TOKEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Token - 도매인 테스트")
public class TokenTest {

    private final User user = UserFixture.A.toEntity();
    private final String refreshToken = REFRESH_TOKEN;

    @Test
    @DisplayName("새로운 토큰을 발급을 한다")
    void issue() {
        //when
        Token token = Token.issue(user, refreshToken);

        //then
        assertAll(
                () -> assertThat(token.getUser()).isEqualTo(user),
                () -> assertThat(token.getRefreshToken()).isEqualTo(refreshToken)
        );
    }

    @Test
    @DisplayName("리프래쉬 토큰을 업데이트한다")
    void updateRefreshToken() {
        //given
        Token token = Token.issue(user, refreshToken);

        //when
        String newRefreshToken = "NewRefreshToken";
        token.updateRefreshToken(newRefreshToken);

        //then
        assertThat(token.getRefreshToken()).isEqualTo(newRefreshToken);
    }
}
