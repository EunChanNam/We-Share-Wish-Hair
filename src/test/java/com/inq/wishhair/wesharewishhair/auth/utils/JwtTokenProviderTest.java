package com.inq.wishhair.wesharewishhair.auth.utils;

import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.global.utils.TokenUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.inq.wishhair.wesharewishhair.global.utils.TokenUtils.REFRESH_TOKEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

    @Nested
    @DisplayName("토큰이 유효한지 검사한다")
    class validateToken {
        @Test
        @DisplayName("만료된 토큰으로 검증에 실패한다")
        void test4() {
            //given
            provider = new JwtTokenProvider(secretKey, failValidity, failValidity);
            String token = provider.createAccessToken(1L);

            //when, then
            assertThatThrownBy(() -> provider.isValidToken(token))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(ErrorCode.AUTH_EXPIRED_TOKEN.getMessage());
        }

        @Test
        @DisplayName("올바르지 못한 토큰으로 검증에 실패한다")
        void test5() {
            //given
            provider = new JwtTokenProvider(secretKey, successAccessTokenValidity, successRefreshTokenValidity);
            String wrongToken = provider.createRefreshToken(1L) + "fail";

            //when, then
            assertAll(
                    //틀린 암호키로 만들어진 토큰
                    () -> assertThatThrownBy(() -> provider.isValidToken(REFRESH_TOKEN))
                            .isInstanceOf(WishHairException.class)
                            .hasMessageContaining(ErrorCode.AUTH_INVALID_TOKEN.getMessage()),
                    //단순히 틀린 토큰
                    () -> assertThatThrownBy(() -> provider.isValidToken(wrongToken))
                            .isInstanceOf(WishHairException.class)
                            .hasMessageContaining(ErrorCode.AUTH_INVALID_TOKEN.getMessage())
            );
        }

        @Test
        @DisplayName("토큰 검증에 성공한다")
        void test6() {
            //given
            provider = new JwtTokenProvider(secretKey, successAccessTokenValidity, successRefreshTokenValidity);
            String refreshToken = provider.createRefreshToken(1L);

            //when
            boolean result = provider.isValidToken(refreshToken);

            //then
            assertThat(result).isTrue();
        }
    }
}
