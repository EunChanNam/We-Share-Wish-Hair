package com.inq.wishhair.wesharewishhair.auth.infra.oauth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@DisplayName("OAuthProperties 테스트")
public class OAuthPropertiesTest {

    @Autowired
    private OAuthProperties properties;

    @Test
    @DisplayName("Google OAuth properties 주입 테스트")
    void validateProperties() {
        //then
        assertAll(
                () -> assertThat(properties.getAuthUrl()).isEqualTo("https://accounts.google.com/o/oauth2/v2/auth"),
                () -> assertThat(properties.getGrantType()).isEqualTo("authorization_code"),
                () -> assertThat(properties.getClientId()).isEqualTo("client-id"),
                () -> assertThat(properties.getTokenUrl()).isEqualTo("https://www.googleapis.com/oauth2/v4/token"),
                () -> assertThat(properties.getUserInfoUrl()).isEqualTo("https://www.googleapis.com/oauth2/v3/userinfo"),
                () -> assertThat(properties.getRedirectUrl()).isEqualTo("redirect-url"),
                () -> assertThat(properties.getScope()).containsExactlyInAnyOrder("profile", "email")
        );
    }
}
