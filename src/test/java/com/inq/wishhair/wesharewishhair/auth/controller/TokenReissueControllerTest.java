package com.inq.wishhair.wesharewishhair.auth.controller;

import com.inq.wishhair.wesharewishhair.auth.service.dto.response.TokenResponse;
import com.inq.wishhair.wesharewishhair.global.base.ControllerTest;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.inq.wishhair.wesharewishhair.global.exception.ErrorCode.AUTH_EXPIRED_TOKEN;
import static com.inq.wishhair.wesharewishhair.global.exception.ErrorCode.AUTH_INVALID_TOKEN;
import static com.inq.wishhair.wesharewishhair.global.utils.TokenUtils.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("TokenReissueControllerTest - WebMvcTest")
public class TokenReissueControllerTest extends ControllerTest {

    private static final String BASE_URL = "/api/token/reissue";
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer";

    @Nested
    @DisplayName("토큰 재발급 API")
    class ReissueToken {
        @Test
        @DisplayName("잘못된 Refresh 토큰으로 재발급에 실패해 유효하지 않은 토큰 예외를 던진다")
        void test1() throws Exception {
            //given
            setUpSuccessToken();
            given(tokenReissueService.reissueToken(1L, REFRESH_TOKEN))
                    .willThrow(new WishHairException(AUTH_INVALID_TOKEN));

            //when
            MockHttpServletRequestBuilder requestBuilder = buildReissueRequest();

            //then
            mockMvc.perform(requestBuilder)
                    .andExpectAll(
                            status().isUnauthorized(),
                            jsonPath("$.message").value(AUTH_INVALID_TOKEN.getMessage())
                    );
        }

        @Test
        @DisplayName("만료된 Refresh 토큰으로 재발급에 실패해 만료된 토큰 예외를 던진다")
        void test2() throws Exception {
            //given
            given(provider.isValidToken(REFRESH_TOKEN))
                    .willThrow(new WishHairException(AUTH_EXPIRED_TOKEN));

            //when
            MockHttpServletRequestBuilder requestBuilder = buildReissueRequest();

            //then
            mockMvc.perform(requestBuilder)
                    .andExpectAll(
                            status().isUnauthorized(),
                            jsonPath("$.message").value(AUTH_EXPIRED_TOKEN.getMessage())
                    );
        }

        @Test
        @DisplayName("토큰 재발급에 성공한다")
        void test3() throws Exception {
            //given
            setUpSuccessToken();

            TokenResponse expectedResponse = toResponse();
            given(tokenReissueService.reissueToken(1L, REFRESH_TOKEN))
                    .willReturn(expectedResponse);

            //when
            MockHttpServletRequestBuilder requestBuilder = buildReissueRequest();

            //then
            mockMvc.perform(requestBuilder)
                    .andExpectAll(
                            status().isOk(),
                            jsonPath("$").exists(),
                            jsonPath("$.accessToken").value(expectedResponse.getAccessToken()),
                            jsonPath("$.refreshToken").value(expectedResponse.getRefreshToken())
                    );
        }
    }

    private MockHttpServletRequestBuilder buildReissueRequest() {
        return MockMvcRequestBuilders
                .post(BASE_URL)
                .header(AUTHORIZATION, BEARER + REFRESH_TOKEN);
    }

    private void setUpSuccessToken() {
        given(provider.isValidToken(REFRESH_TOKEN)).willReturn(true);
        given(provider.getId(REFRESH_TOKEN)).willReturn(1L);
    }
}
