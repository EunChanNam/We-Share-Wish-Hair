package com.inq.wishhair.wesharewishhair.login.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.inq.wishhair.wesharewishhair.common.base.ControllerTest;
import com.inq.wishhair.wesharewishhair.domain.login.dto.UserSessionDto;
import com.inq.wishhair.wesharewishhair.domain.user.User;
import com.inq.wishhair.wesharewishhair.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.login.controller.utils.LoginRequestUtils;
import com.inq.wishhair.wesharewishhair.web.login.dto.LoginRequest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("LoginControllerTest - WebMvcTest")
public class LoginControllerTest extends ControllerTest {

    private static String LOGIN_URL = "/api/login";

    @Test
    @DisplayName("올바른 로그인 아이디, 비밀번호를 통해 로그인을 성공한다.")
    void test1() throws Exception {
        //given
        LoginRequest request = LoginRequestUtils.createRequest();
        given(loginService.login(request.getLoginId(), request.getPw()))
                .willReturn(getSessionDto());

        //todo 세션에 등록되는 정보는 테스트를 따로 안해줘도 되는지

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(LOGIN_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJsonAsString(request));

        mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isNoContent(),
                        jsonPath("$").doesNotExist()
                );
    }

    //todo common 패키지에 공용메서드로 분리하기
    private UserSessionDto getSessionDto() {
        return new UserSessionDto(createUser());
    }

    private User createUser() {
        return UserFixture.A.toEntity();
    }
}
