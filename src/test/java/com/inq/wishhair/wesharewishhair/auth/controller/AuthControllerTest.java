package com.inq.wishhair.wesharewishhair.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.inq.wishhair.wesharewishhair.auth.controller.dto.request.LoginRequest;
import com.inq.wishhair.wesharewishhair.auth.controller.utils.LoginRequestUtils;
import com.inq.wishhair.wesharewishhair.auth.service.dto.response.TokenResponse;
import com.inq.wishhair.wesharewishhair.global.base.ControllerTest;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.inq.wishhair.wesharewishhair.global.utils.TokenUtils.*;
import static com.inq.wishhair.wesharewishhair.global.exception.ErrorCode.AUTH_REQUIRED_LOGIN;
import static com.inq.wishhair.wesharewishhair.global.exception.ErrorCode.LOGIN_FAIL;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("AuthControllerTest - WebMvcTest")
public class AuthControllerTest extends ControllerTest {

    private static final String LOGIN_URL = "/api/login";
    private static final String LOGOUT_URL = "/api/logout";
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer";

    @Nested
    @DisplayName("로그인 API")
    class login {
        @Test
        @DisplayName("로그인에 성공하고 응답 토큰을 받는다")
        void test1() throws Exception {
            //given
            LoginRequest request = LoginRequestUtils.createRequest();
            TokenResponse expectedResponse = toResponse();
            given(authService.login(request.getEmail(), request.getPw()))
                    .willReturn(expectedResponse);

            //when
            MockHttpServletRequestBuilder requestBuilder = buildLoginRequest(request);

            //then
            mockMvc.perform(requestBuilder)
                    .andExpectAll(
                            status().isOk(),
                            jsonPath("$.accessToken").value(expectedResponse.getAccessToken()),
                            jsonPath("$.refreshToken").value(expectedResponse.getRefreshToken())
                    );
        }

        @Test
        @DisplayName("로그인에 실패하면 로그인 실패 400 예외를 던진다")
        void test2() throws Exception {
            //given
            LoginRequest request = LoginRequestUtils.createRequest();
            given(authService.login(request.getEmail(), request.getPw()))
                    .willThrow(new WishHairException(LOGIN_FAIL));

            //when
            MockHttpServletRequestBuilder requestBuilder = buildLoginRequest(request);

            //then
            mockMvc.perform(requestBuilder)
                    .andExpectAll(
                            status().isBadRequest(),
                            jsonPath("$.code").value(LOGIN_FAIL.getCode()),
                            jsonPath("$.message").value(LOGIN_FAIL.getMessage())
                    );
        }
    }

    @Nested
    @DisplayName("로그아웃 API")
    class Logout {
        @Test
        @DisplayName("헤더에 토큰을 포합하지 않으면 401 예외를 던진다")
        void test3() throws Exception {
            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .post(LOGOUT_URL);

            //then
            mockMvc.perform(requestBuilder)
                    .andExpectAll(
                            status().isUnauthorized(),
                            jsonPath("$.code").value(AUTH_REQUIRED_LOGIN.getCode()),
                            jsonPath("$.message").value(AUTH_REQUIRED_LOGIN.getMessage())
                    );
        }

        @Test
        @DisplayName("로그아웃을 성공한다")
        void test4() throws Exception {
            //given
            given(provider.isValidToken(ACCESS_TOKEN)).willReturn(true);
            given(provider.getId(ACCESS_TOKEN)).willReturn(1L);

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .post(LOGOUT_URL)
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN);

            //then
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isNoContent());
        }
    }

    private MockHttpServletRequestBuilder buildLoginRequest(LoginRequest request) throws JsonProcessingException {
        return MockMvcRequestBuilders
                .post(LOGIN_URL)
                .content(toJson(request))
                .contentType(MediaType.APPLICATION_JSON);
    }
}
