package com.inq.wishhair.wesharewishhair.auth.controller;

import com.inq.wishhair.wesharewishhair.auth.controller.dto.request.LoginRequest;
import com.inq.wishhair.wesharewishhair.auth.controller.utils.LoginRequestUtil;
import com.inq.wishhair.wesharewishhair.global.base.ControllerTest;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.inq.wishhair.wesharewishhair.fixture.TokenFixture.A;
import static com.inq.wishhair.wesharewishhair.global.exception.ErrorCode.LOGIN_FAIL;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("AuthControllerTest - WebMvcTest")
public class AuthControllerTest extends ControllerTest {

    private static final String LOGIN_URL = "/api/login";

    @Nested
    @DisplayName("로그인 API")
    class login {
        @Test
        @DisplayName("로그인에 성공하고 응답 토큰을 받는다")
        void test1() throws Exception {
            //given
            LoginRequest request = LoginRequestUtil.createRequest();
            given(authService.login(request.getLoginId(), request.getPw()))
                    .willReturn(A.toTokenResponse());

            //when
            MockHttpServletRequestBuilder requestBuilder = buildLoginRequest(request);

            //then
            mockMvc.perform(requestBuilder)
                    .andExpectAll(
                            status().isOk(),
                            jsonPath("$").exists(),
                            jsonPath("$.accessToken").exists(),
                            jsonPath("$.accessToken").value(A.getAccessToken()),
                            jsonPath("$.refreshToken").exists(),
                            jsonPath("$.refreshToken").value(A.getRefreshToken())
                    );
        }

        @Test
        @DisplayName("로그인에 실패하면 로그인 실패 400 예외를 던진다")
        void test2() throws Exception {
            //given
            LoginRequest request = LoginRequestUtil.createRequest();
            given(authService.login(request.getLoginId(), request.getPw()))
                    .willThrow(new WishHairException(LOGIN_FAIL));

            //when
            MockHttpServletRequestBuilder requestBuilder = buildLoginRequest(request);

            //then
            mockMvc.perform(requestBuilder)
                    .andExpectAll(
                            status().isBadRequest(),
                            jsonPath("$").exists(),
                            jsonPath("$.code").exists(),
                            jsonPath("$.code").value(LOGIN_FAIL.getCode()),
                            jsonPath("$.message").exists(),
                            jsonPath("$.message").value(LOGIN_FAIL.getMessage())
                    );
        }
    }

    private MockHttpServletRequestBuilder buildLoginRequest(LoginRequest request) {
        return MockMvcRequestBuilders
                .post(LOGIN_URL)
                .param("loginId", request.getLoginId())
                .param("pw", request.getPw());
    }
}
