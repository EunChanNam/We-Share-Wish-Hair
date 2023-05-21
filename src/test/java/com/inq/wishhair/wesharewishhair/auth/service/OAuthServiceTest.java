package com.inq.wishhair.wesharewishhair.auth.service;

import com.inq.wishhair.wesharewishhair.auth.exception.WishHairOAuthException;
import com.inq.wishhair.wesharewishhair.auth.infra.oauth.OAuthConnector;
import com.inq.wishhair.wesharewishhair.auth.infra.oauth.dto.response.GoogleTokenResponse;
import com.inq.wishhair.wesharewishhair.auth.infra.oauth.dto.response.GoogleUserResponse;
import com.inq.wishhair.wesharewishhair.auth.infra.oauth.dto.response.OAuthUserResponse;
import com.inq.wishhair.wesharewishhair.auth.service.dto.response.LoginResponse;
import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.inq.wishhair.wesharewishhair.global.fixture.UserFixture.A;
import static com.inq.wishhair.wesharewishhair.global.fixture.UserFixture.B;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;

@DisplayName("OAuthService test - SpringBootTest")
public class OAuthServiceTest extends ServiceTest {
    private static final String CODE = "code";
    private static final String ACCESS_TOKEN = "access-token";

    @Autowired
    private OAuthService oauthService;

    @MockBean
    private OAuthConnector connector;

    private final User user = A.toEntity();

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
            //given
            GoogleTokenResponse tokenResponse = new GoogleTokenResponse(ACCESS_TOKEN);
            given(connector.requestToken(CODE)).willReturn(tokenResponse);

            OAuthUserResponse userResponse = new GoogleUserResponse(A.getName(), A.getEmail());
            given(connector.requestUserInfo(ACCESS_TOKEN)).willReturn(userResponse);

            //when
            LoginResponse result = oauthService.login(CODE);

            //then
            assertAll(
                    () -> assertThat(result).isNotNull(),
                    () -> assertThat(result.getUserInfo().getNickname()).isEqualTo(A.getNickname()),
                    () -> assertThat(result.getUserInfo().isHasFaceShape()).isFalse(),
                    () -> assertThat(result.getAccessToken()).isNotNull(),
                    () -> assertThat(result.getRefreshToken()).isNotNull()
            );
        }

        @Test
        @DisplayName("Google Server 통신간 오류 혹은 잘못된 authorization code 로 실패한다")
        void failByGoogleServer() {
            //given
            ErrorCode expectedError = ErrorCode.GOOGLE_OAUTH_EXCEPTION;
            given(connector.requestToken(CODE)).willThrow(new WishHairException(expectedError));

            //when
            assertThatThrownBy(() -> oauthService.login(CODE))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(expectedError.getMessage());
        }

        @Test
        @DisplayName("존재하지 않는 회원으로 회원가입을 하도록 예외를 던진다")
        void failByNoSignUp() {
            //given
            GoogleTokenResponse tokenResponse = new GoogleTokenResponse(ACCESS_TOKEN);
            given(connector.requestToken(CODE)).willReturn(tokenResponse);

            OAuthUserResponse userResponse = new GoogleUserResponse(B.getName(), B.getEmail());
            given(connector.requestUserInfo(ACCESS_TOKEN)).willReturn(userResponse);

            //when
            assertThatThrownBy(() -> oauthService.login(CODE))
                    .isInstanceOf(WishHairOAuthException.class);
        }
    }
}
