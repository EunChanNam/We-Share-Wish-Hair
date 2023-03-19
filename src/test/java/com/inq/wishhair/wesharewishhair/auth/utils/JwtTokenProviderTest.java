package com.inq.wishhair.wesharewishhair.auth.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.setAllowComparingPrivateFields;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("JwtTokenProviderTest")
public class JwtTokenProviderTest {

    private static final String secretKey = "eyasfQciOiJIUzI1NiIvLoAR5cCI6IkpXVCJ9.eyJzdWIiOiIiLCLoCP1lIjoiSm9obiBEV9UiLCJpYXBCusE1MTYyMzkwMjJ9.123asdfa8s7d6f987qweahqwculaoxce80k1i2o387tg";
    private static final long successAccessTokenValidity = 7200L;
    private static final long successRefreshTokenValidity = 1209600L;
    private static final long failValidity = 0L;
    private JwtTokenProvider provider;

    @Test
    @DisplayName("Access 토큰을 생성한다")
    void test1() {
        //given
        provider = new JwtTokenProvider(secretKey, successAccessTokenValidity, successRefreshTokenValidity);

        //when
        String accessToken = provider.createAccessToken(1L);

        //then
        assertAll(
                () -> assertThat(accessToken).isNotNull(),
                () -> assertThat(provider.getId(accessToken)).isEqualTo(1L),
                () -> assertThat(provider.isValidToken(accessToken)).isTrue()
        );
    }

    @Test
    @DisplayName("Refresh 토큰을 생성한다")
    void test2() {
        //given
        provider = new JwtTokenProvider(secretKey, successAccessTokenValidity, successRefreshTokenValidity);

        //when
        String accessToken = provider.createRefreshToken(1L);

        //then
        assertAll(
                () -> assertThat(accessToken).isNotNull(),
                () -> assertThat(provider.getId(accessToken)).isEqualTo(1L),
                () -> assertThat(provider.isValidToken(accessToken)).isTrue()
        );
    }

    @Test
    @DisplayName("토큰에서 아이디를 추출한다")
    void test3() {
        //given
        provider = new JwtTokenProvider(secretKey, successAccessTokenValidity, successRefreshTokenValidity);
        String token = provider.createAccessToken(1L);

        //when
        Long result = provider.getId(token);

        //then
        assertThat(result).isEqualTo(1L);
    }
}
