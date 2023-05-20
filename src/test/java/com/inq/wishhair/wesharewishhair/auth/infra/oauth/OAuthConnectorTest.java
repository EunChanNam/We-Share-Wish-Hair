package com.inq.wishhair.wesharewishhair.auth.infra.oauth;

import com.inq.wishhair.wesharewishhair.auth.infra.oauth.dto.response.GoogleTokenResponse;
import com.inq.wishhair.wesharewishhair.auth.infra.oauth.dto.response.GoogleUserResponse;
import com.inq.wishhair.wesharewishhair.auth.infra.oauth.dto.response.OAuthTokenResponse;
import com.inq.wishhair.wesharewishhair.auth.infra.oauth.dto.response.OAuthUserResponse;
import com.inq.wishhair.wesharewishhair.global.base.InfraTest;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.global.utils.TokenUtils;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static com.inq.wishhair.wesharewishhair.global.utils.TokenUtils.ACCESS_TOKEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@DisplayName("OAuthConnector - InfraTest")
public class OAuthConnectorTest extends InfraTest {

    @Mock
    private OAuthProperties properties;

    @Mock
    private RestTemplate restTemplate;

    private OAuthConnector connector;

    @BeforeEach
    void setUp() {
        connector = new GoogleConnector(restTemplate, properties);
        //given
//        given(properties.getAuthUrl()).willReturn("https://accounts.google.com/o/oauth2/v2/auth");
//        given(properties.getGrantType()).willReturn("authorization_code");
//        given(properties.getTokenUrl()).willReturn("https://www.googleapis.com/oauth2/v4/token");
//        given(properties.getRedirectUrl()).willReturn("redirect-url");
//        given(properties.getClientId()).willReturn("client-id");
//        given(properties.getUserInfoUrl()).willReturn("https://www.googleapis.com/oauth2/v3/userinfo");
//        given(properties.getScope()).willReturn(Set.of("profile", "email"));
    }

    @Nested
    @DisplayName("Google Token 조회 서비스")
    class requestToken {
        private static final String CODE = "code";

        @BeforeEach
        void setUp() {
            //given
            given(properties.getGrantType()).willReturn("authorization_code");
            given(properties.getTokenUrl()).willReturn("https://www.googleapis.com/oauth2/v4/token");
            given(properties.getRedirectUrl()).willReturn("redirect-url");
            given(properties.getClientId()).willReturn("client-id");
        }

        @Test
        @DisplayName("토큰을 성공적으로 조회한다")
        void success() {
            //given
            GoogleTokenResponse response = TokenUtils.toGoogleTokenResponse();
            ResponseEntity<GoogleTokenResponse> responseEntity = ResponseEntity.ok(response);
            given(restTemplate.postForEntity(eq(properties.getTokenUrl()), any(), eq(GoogleTokenResponse.class)))
                    .willReturn(responseEntity);

            //when
            OAuthTokenResponse result = connector.requestToken(CODE);

            //then
            assertThat(result.getAccessToken()).isEqualTo(ACCESS_TOKEN);
        }

        @Test
        @DisplayName("Google Server 와 통신간 오류로 실패한다")
        void failByGoogleServer() {
            //given
            ErrorCode expectedError = ErrorCode.GOOGLE_OAUTH_EXCEPTION;
            given(restTemplate.postForEntity(eq(properties.getTokenUrl()), any(), eq(GoogleTokenResponse.class)))
                    .willThrow(new WishHairException(expectedError));

            //when, then
            assertThatThrownBy(() -> connector.requestToken(CODE))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(expectedError.getMessage());
        }

        @Test
        @DisplayName("Google Server 의 올바르지 않은 응답으로 실패한다")
        void failByInvalidResponse() {
            //given
            GoogleTokenResponse response = TokenUtils.toGoogleTokenResponse();
            ResponseEntity<GoogleTokenResponse> responseEntity = ResponseEntity.badRequest().body(response);
            given(restTemplate.postForEntity(eq(properties.getTokenUrl()), any(), eq(GoogleTokenResponse.class)))
                    .willReturn(responseEntity);

            //when, then
            assertThatThrownBy(() -> connector.requestToken(CODE))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(ErrorCode.GOOGLE_OAUTH_EXCEPTION.getMessage());
        }
    }

    @Nested
    @DisplayName("사용자 정보 조회 서비스")
    class requestUserInfo {
        private static final String ACCESS_TOKEN = "access-token";

        @BeforeEach
        void setUp() {
            //given
            given(properties.getUserInfoUrl()).willReturn("https://www.googleapis.com/oauth2/v3/userinfo");
        }

        @Test
        @DisplayName("사용자 정보를 성공적으로 조회한다")
        void success() {
            //given
            GoogleUserResponse userResponse = new GoogleUserResponse("hello", "hello1234@naver.com");
            ResponseEntity<GoogleUserResponse> responseEntity = ResponseEntity.ok(userResponse);

            given(restTemplate.postForEntity(eq(properties.getUserInfoUrl()), any(), eq(GoogleUserResponse.class)))
                    .willReturn(responseEntity);

            //when
            OAuthUserResponse result = connector.requestUserInfo(ACCESS_TOKEN);

            //then
            assertAll(
                    () -> assertThat(result.getName()).isEqualTo(userResponse.getName()),
                    () -> assertThat(result.getEmail()).isEqualTo(userResponse.getEmail())
            );
        }

        @Test
        @DisplayName("Google Server 와 통신간 오류로 실패한다")
        void failByGoogleServer() {
            //given
            ErrorCode expectedError = ErrorCode.GOOGLE_OAUTH_EXCEPTION;
            given(restTemplate.postForEntity(eq(properties.getUserInfoUrl()), any(), eq(GoogleUserResponse.class)))
                    .willThrow(new WishHairException(expectedError));

            //when, then
            assertThatThrownBy(() -> connector.requestUserInfo(ACCESS_TOKEN))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(expectedError.getMessage());
        }

        @Test
        @DisplayName("Google Server 의 올바르지 않은 응답으로 실패한다")
        void failByInvalidResponse() {
            //given
            GoogleUserResponse userResponse = new GoogleUserResponse("hello", "hello1234@naver.com");
            ResponseEntity<GoogleUserResponse> responseEntity = ResponseEntity.badRequest().body(userResponse);
            given(restTemplate.postForEntity(eq(properties.getUserInfoUrl()), any(), eq(GoogleUserResponse.class)))
                    .willReturn(responseEntity);

            //when, then
            assertThatThrownBy(() -> connector.requestUserInfo(ACCESS_TOKEN))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(ErrorCode.GOOGLE_OAUTH_EXCEPTION.getMessage());
        }
    }
}
