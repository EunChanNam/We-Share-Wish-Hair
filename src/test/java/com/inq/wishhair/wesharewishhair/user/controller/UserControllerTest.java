package com.inq.wishhair.wesharewishhair.user.controller;

import com.inq.wishhair.wesharewishhair.global.base.ControllerTest;
import com.inq.wishhair.wesharewishhair.user.controller.utils.UserCreateRequestUtils;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.UserCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("UserControllerTest - Mock")
public class UserControllerTest extends ControllerTest {

    private static final String JOIN_URL = "/api/user";

    @Test
    @DisplayName("성공적인 회원가입 테스트")
    void successCreateUserTest() throws Exception {

        UserCreateRequest request = UserCreateRequestUtils.createRequest();
        given(userService.createUser(request.toEntity())).willReturn(1L);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(JOIN_URL)
                .content(toJson(request))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").doesNotExist());
    }
}
