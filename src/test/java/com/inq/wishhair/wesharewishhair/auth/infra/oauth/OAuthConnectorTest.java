package com.inq.wishhair.wesharewishhair.auth.infra.oauth;

import com.inq.wishhair.wesharewishhair.auth.infra.oauth.dto.response.GoogleTokenResponse;
import com.inq.wishhair.wesharewishhair.auth.infra.oauth.dto.response.OAuthTokenResponse;
import com.inq.wishhair.wesharewishhair.global.base.InfraTest;
import com.inq.wishhair.wesharewishhair.global.utils.TokenUtils;
import org.apache.http.HttpEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static com.inq.wishhair.wesharewishhair.global.utils.TokenUtils.ACCESS_TOKEN;
import static org.assertj.core.api.Assertions.assertThat;
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
//        given(properties.getAuthUrl()).willReturn("https://accounts.google.com/o/oauth2/v2/auth");
//        given(properties.getGrantType()).willReturn("authorization_code");
//        given(properties.getTokenUrl()).willReturn("https://www.googleapis.com/oauth2/v4/token");
//        given(properties.getUserInfoUrl()).willReturn("https://www.googleapis.com/oauth2/v3/userinfo");
//        given(properties.getClientId()).willReturn("client-id");
//        given(properties.getScope()).willReturn(Set.of("profile", "email"));
//        given(properties.getRedirectUrl()).willReturn("redirect-url");
    }

    @Nested
    @DisplayName("Google Token 조회 서비스")
    class requestToken {
        @Test
        @DisplayName("토큰을 성공적으로 조회한다")
        void success() {
            //given
            given(properties.getGrantType()).willReturn("authorization_code");
            given(properties.getClientId()).willReturn("client_id");
            given(properties.getTokenUrl()).willReturn("https://www.googleapis.com/oauth2/v4/token");

            GoogleTokenResponse response = TokenUtils.toGoogleTokenResponse();
            ResponseEntity<GoogleTokenResponse> responseEntity = ResponseEntity.ok(response);
            given(restTemplate.postForEntity(eq(properties.getTokenUrl()), any(HttpEntity.class), eq(GoogleTokenResponse.class)))
                    .willReturn(responseEntity);

            //when
            OAuthTokenResponse result = connector.requestToken("code");

            //then
            assertThat(result.getAccessToken()).isEqualTo(ACCESS_TOKEN);
        }
    }
}
