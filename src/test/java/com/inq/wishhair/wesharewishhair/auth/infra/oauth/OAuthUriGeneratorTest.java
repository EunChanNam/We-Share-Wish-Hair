package com.inq.wishhair.wesharewishhair.auth.infra.oauth;

import com.inq.wishhair.wesharewishhair.global.base.InfraTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;

@DisplayName("OAuthUriGenerator 테스트 - InfraTest")
public class OAuthUriGeneratorTest extends InfraTest {

    @Mock
    private OAuthProperties properties;

    private OAuthUriGenerator uriGenerator;

    @BeforeEach
    void setUp() {
        uriGenerator = new GoogleOAuthUriGenerator(properties);
        given(properties.getAuthUrl()).willReturn("https://accounts.google.com/o/oauth2/v2/auth");
        given(properties.getClientId()).willReturn("client-id");
        given(properties.getScope()).willReturn(Set.of("profile", "email"));
        given(properties.getRedirectUrl()).willReturn("redirect-url");
    }

    @Test
    @DisplayName("OAuth Uri 를 생성한다")
    void generate() {
        //when
        String result = uriGenerator.generate();

        //then
        MultiValueMap<String, String> queryParams = UriComponentsBuilder
                .fromUriString(result)
                .build()
                .getQueryParams();

        assertAll(
                () -> assertThat(queryParams.getFirst("response_type")).isEqualTo("code"),
                () -> assertThat(queryParams.getFirst("client_id")).isEqualTo(properties.getClientId()),
                () -> assertThat(queryParams.getFirst("scope")).isEqualTo(String.join(" ", properties.getScope())),
                () -> assertThat(queryParams.getFirst("redirect_uri")).isEqualTo(properties.getRedirectUrl())
        );
    }
}
