package com.inq.wishhair.wesharewishhair.login.controller;

import com.inq.wishhair.wesharewishhair.common.base.ControllerTest;
import com.inq.wishhair.wesharewishhair.common.utils.UserSessionDtoUtils;
import com.inq.wishhair.wesharewishhair.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.login.controller.utils.LoginRequestUtils;
import com.inq.wishhair.wesharewishhair.web.login.dto.LoginRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("LoginControllerTest - WebMvcTest")
public class LoginControllerTest extends ControllerTest {

    private static final String LOGIN_URL = "/api/login";

    @Test
    @DisplayName("올바른 로그인 아이디, 비밀번호를 통해 로그인을 성공한다.")
    void test1() throws Exception {
        //given
        LoginRequest request = LoginRequestUtils.createRequest();
        given(loginService.login(request.getLoginId(), request.getPw()))
                .willReturn(UserSessionDtoUtils.getASessionDto());

        //todo 세션에 등록되는 정보는 테스트를 따로 안해줘도 되는지
        //when
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(LOGIN_URL)
                .param("loginId", request.getLoginId())
                .param("pw", request.getPw());

        //then
        mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isNoContent(),
                        jsonPath("$").doesNotExist()
                );
    }

    @Test
    @DisplayName("올바르지 않은 아이디로 시도해 로그인에 실패한다.")
    void test2() throws Exception {
        //given
        ErrorCode expectedError = ErrorCode.LOGIN_FAIL;
        LoginRequest request = LoginRequestUtils.createWrongLoginIdRequest();
        given(loginService.login(request.getLoginId(), request.getPw()))
                .willThrow(new WishHairException(expectedError));

        //when
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(LOGIN_URL)
                .param("loginId", request.getLoginId())
                .param("pw", request.getPw());

        //then
        mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$").exists(),
                        jsonPath("$.code").value(expectedError.getCode()),
                        jsonPath("$.message").value(expectedError.getMessage())
                );
    }

    @Test
    @DisplayName("올바르지 않은 비밀번호로 시도해 로그인에 실패한다.")
    void test3() throws Exception {
        //given
        ErrorCode expectedError = ErrorCode.LOGIN_FAIL;
        LoginRequest request = LoginRequestUtils.createWrongPwRequest();
        given(loginService.login(request.getLoginId(), request.getPw()))
                .willThrow(new WishHairException(expectedError));

        //when
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(LOGIN_URL)
                .param("loginId", request.getLoginId())
                .param("pw", request.getPw());

        //then
        mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$").exists(),
                        jsonPath("$.code").value(expectedError.getCode()),
                        jsonPath("$.message").value(expectedError.getMessage())
                );
    }
}
