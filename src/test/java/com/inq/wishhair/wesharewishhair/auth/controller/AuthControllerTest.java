package com.inq.wishhair.wesharewishhair.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.inq.wishhair.wesharewishhair.auth.controller.dto.request.LoginRequest;
import com.inq.wishhair.wesharewishhair.auth.controller.utils.LoginRequestUtils;
import com.inq.wishhair.wesharewishhair.auth.service.dto.response.TokenResponse;
import com.inq.wishhair.wesharewishhair.global.base.ControllerTest;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
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
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
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
        void success() throws Exception {
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
                    ).andDo(
                            restDocs.document(
                                    requestFields(
                                            fieldWithPath("email").description("이메일 (아이디)")
                                                    .attributes(constraint("필수 입니다")),
                                            fieldWithPath("pw").description("비밀번호")
                                                    .attributes(constraint("필수 입니다"))
                                    ),
                                    responseFields(
                                            fieldWithPath("accessToken").description("Access 토큰 (Expire 2시간)"),
                                            fieldWithPath("refreshToken").description("Refresh 토큰 (Expire 2주)")
                                    )
                            )
                    );
        }

        @Test
        @DisplayName("로그인에 실패하면 로그인 실패 400 예외를 던진다")
        void loginFail() throws Exception {
            //given
            LoginRequest request = LoginRequestUtils.createRequest();
            ErrorCode expectedError = LOGIN_FAIL;
            given(authService.login(request.getEmail(), request.getPw()))
                    .willThrow(new WishHairException(expectedError));

            //when
            MockHttpServletRequestBuilder requestBuilder = buildLoginRequest(request);

            //then
            assertException(expectedError, requestBuilder, status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("로그아웃 API")
    class Logout {
        @Test
        @DisplayName("헤더에 토큰을 포합하지 않으면 401 예외를 던진다")
        void failByNoAccessToken() throws Exception {
            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .post(LOGOUT_URL);

            //then
            assertException(AUTH_REQUIRED_LOGIN, requestBuilder, status().isUnauthorized());
        }

        @Test
        @DisplayName("로그아웃을 성공한다")
        void success() throws Exception {
            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .post(LOGOUT_URL)
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN);

            //then
            mockMvc.perform(requestBuilder)
                    .andExpectAll(
                            status().isOk(),
                            jsonPath("$").exists(),
                            jsonPath("$.success").value(true)
                    ).andDo(
                            restDocs.document(
                                    requestHeaders(
                                            headerWithName(AUTHORIZATION).description("Bearer + Access Token")
                                                    .attributes(constraint("반드시 포함"))
                                    ),
                                    responseFields(
                                            fieldWithPath("success").description("성공 여부")
                                    )
                            )
                    );
        }
    }

    private MockHttpServletRequestBuilder buildLoginRequest(LoginRequest request) throws JsonProcessingException {
        return MockMvcRequestBuilders
                .post(LOGIN_URL)
                .content(toJson(request))
                .contentType(MediaType.APPLICATION_JSON);
    }
}
