package com.inq.wishhair.wesharewishhair.user.controller;

import com.inq.wishhair.wesharewishhair.global.base.ControllerTest;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.user.controller.utils.UserCreateRequestUtils;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.UserCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.inq.wishhair.wesharewishhair.user.controller.utils.UserCreateRequestUtils.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("UserControllerTest - Mock")
public class UserControllerTest extends ControllerTest {

    private static final String JOIN_URL = "/api/user";

    @Nested
    @DisplayName("회원가입 API 테스트")
    class createUser {
        @Test
        @DisplayName("성공적인 회원가입 테스트")
        void success() throws Exception {
            //given
            UserCreateRequest request = successRequest();
            given(userService.createUser(request.toEntity())).willReturn(1L);

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .post(JOIN_URL)
                    .content(toJson(request))
                    .contentType(APPLICATION_JSON);

            //then
            mockMvc.perform(requestBuilder)
                    .andExpectAll(
                            status().isCreated(),
                            jsonPath("$").exists(),
                            jsonPath("$.success").value(true)
                    );
        }

        @Test
        @DisplayName("올바르지 않은 이메일 형식으로 400 예외를 던진다")
        void failByEmail() throws Exception {
            //given
            UserCreateRequest request = WrongEmailRequest();

            ErrorCode expectedError = ErrorCode.USER_INVALID_EMAIL;

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .post(JOIN_URL)
                    .content(toJson(request))
                    .contentType(APPLICATION_JSON);

            //then
            mockMvc.perform(requestBuilder)
                    .andExpectAll(
                            status().isBadRequest(),
                            jsonPath("$").exists(),
                            jsonPath("$.message").value(expectedError.getMessage())
                    );
        }
    }
}
