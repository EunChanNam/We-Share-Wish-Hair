package com.inq.wishhair.wesharewishhair.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.inq.wishhair.wesharewishhair.global.base.ControllerTest;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.UserCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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
            MockHttpServletRequestBuilder requestBuilder = buildJoinRequest(request);

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
            UserCreateRequest request = wrongEmailRequest();

            ErrorCode expectedError = ErrorCode.USER_INVALID_EMAIL;

            //when
            MockHttpServletRequestBuilder requestBuilder = buildJoinRequest(request);

            //then
            performAndAssertException(expectedError, requestBuilder);
        }

        @Test
        @DisplayName("올바르지 않은 닉네임으로 400 예외를 던진다")
        void failByNickname() throws Exception {
            //given
            UserCreateRequest request = wrongNicknameRequest();

            ErrorCode expectedError = ErrorCode.USER_INVALID_NICKNAME;

            //when
            MockHttpServletRequestBuilder requestBuilder = buildJoinRequest(request);

            //then
            performAndAssertException(expectedError, requestBuilder);
        }

        @Test
        @DisplayName("올바르지 않은 비밀번호 형식으로 400 예외를 던진다")
        void failByPassword() throws Exception {
            //given
            UserCreateRequest request = wrongPasswordRequest();

            ErrorCode expectedError = ErrorCode.USER_INVALID_PASSWORD;

            //when
            MockHttpServletRequestBuilder requestBuilder = buildJoinRequest(request);

            //then
            performAndAssertException(expectedError, requestBuilder);
        }

        private MockHttpServletRequestBuilder buildJoinRequest(UserCreateRequest request) throws JsonProcessingException {
            return MockMvcRequestBuilders
                    .post(JOIN_URL)
                    .content(toJson(request))
                    .contentType(APPLICATION_JSON);
        }

    }



    private void performAndAssertException(ErrorCode expectedError, MockHttpServletRequestBuilder requestBuilder) throws Exception {
        mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$").exists(),
                        jsonPath("$.message").value(expectedError.getMessage())
                );
    }
}


