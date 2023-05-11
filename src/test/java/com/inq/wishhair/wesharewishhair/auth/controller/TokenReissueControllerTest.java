package com.inq.wishhair.wesharewishhair.auth.controller;

import com.inq.wishhair.wesharewishhair.auth.service.dto.response.TokenResponse;
import com.inq.wishhair.wesharewishhair.global.base.ControllerTest;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.headers.HeaderDocumentation;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.inq.wishhair.wesharewishhair.global.exception.ErrorCode.AUTH_EXPIRED_TOKEN;
import static com.inq.wishhair.wesharewishhair.global.exception.ErrorCode.AUTH_INVALID_TOKEN;
import static com.inq.wishhair.wesharewishhair.global.utils.TokenUtils.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
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
        @DisplayName("헤더에 refresh 토큰을 포함하지 않아 예외를 던진다")
        void failByNoRefreshToken() throws Exception {
            //when
            ErrorCode expectedError = ErrorCode.AUTH_REQUIRED_LOGIN;
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .post(BASE_URL);

            //then
            assertException(expectedError, requestBuilder, status().isUnauthorized());
        }

        @Test
        @DisplayName("잘못된 Refresh 토큰으로 재발급에 실패해 유효하지 않은 토큰 예외를 던진다")
        void failByInvalidRefreshToken() throws Exception {
            //given
            setUpSuccessToken();
            ErrorCode expectedError = AUTH_INVALID_TOKEN;
            given(tokenReissueService.reissueToken(1L, REFRESH_TOKEN))
                    .willThrow(new WishHairException(expectedError));

            //when
            MockHttpServletRequestBuilder requestBuilder = buildReissueRequest();

            //then
            assertException(expectedError, requestBuilder, status().isUnauthorized());
        }

        @Test
        @DisplayName("만료된 Refresh 토큰으로 재발급에 실패해 만료된 토큰 예외를 던진다")
        void failByExpiredRefreshToken() throws Exception {
            //given
            ErrorCode expectedError = AUTH_EXPIRED_TOKEN;
            given(provider.isValidToken(REFRESH_TOKEN))
                    .willThrow(new WishHairException(expectedError));

            //when
            MockHttpServletRequestBuilder requestBuilder = buildReissueRequest();

            //then
            assertException(expectedError, requestBuilder, status().isUnauthorized());
        }

        @Test
        @DisplayName("토큰 재발급에 성공한다")
        void success() throws Exception {
            //given
            setUpSuccessToken();

            TokenResponse expectedResponse = toTokenResponse();
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
                    ).andDo(
                            restDocs.document(
                                    requestHeaders(
                                            headerWithName(AUTHORIZATION).description("Bearer + Refresh Token")
                                    ),
                                    responseFields(
                                            fieldWithPath("accessToken").description("Access 토큰 (Expire 2시간)"),
                                            fieldWithPath("refreshToken").description("Refresh 토큰 (Expire 2주)")
                                    )
                            )
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
