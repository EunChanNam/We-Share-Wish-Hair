package com.inq.wishhair.wesharewishhair.auth.service;

import com.inq.wishhair.wesharewishhair.auth.domain.Token;
import com.inq.wishhair.wesharewishhair.auth.service.dto.response.TokenResponse;
import com.inq.wishhair.wesharewishhair.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("TokenReissueServiceTest - SpringBootTest")
public class TokenReissueServiceTest extends ServiceTest {

    private static final String REFRESH_TOKEN = "refresh_token";

    @Autowired
    private TokenReissueService tokenReissueService;

    private final User user = UserFixture.A.toEntity();

    @BeforeEach
    void setUp() {
        //given
        userRepository.save(user);
    }

    @Nested
    @DisplayName("토큰을 재발급한다")
    class reissueToken {
        @Test
        @DisplayName("성공적으로 재발급 받는다")
        void test1() {
            //given
            Token originToken = Token.issue(user, REFRESH_TOKEN);
            tokenRepository.save(originToken);

            //when
            TokenResponse result = tokenReissueService.reissueToken(user.getId(), REFRESH_TOKEN);

            //then
            assertAll(
                    () -> assertThat(result).isNotNull(),
                    () -> assertThat(result.getRefreshToken()).isNotEqualTo(REFRESH_TOKEN)
            );
        }
    }
}
