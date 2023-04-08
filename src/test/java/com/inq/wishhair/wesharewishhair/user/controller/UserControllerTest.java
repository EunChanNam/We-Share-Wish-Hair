package com.inq.wishhair.wesharewishhair.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.inq.wishhair.wesharewishhair.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.global.base.ControllerTest;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.UserCreateRequest;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.UserUpdateRequest;
import com.inq.wishhair.wesharewishhair.user.controller.utils.UserUpdateRequestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.inq.wishhair.wesharewishhair.global.utils.TokenUtils.*;
import static com.inq.wishhair.wesharewishhair.user.controller.utils.UserCreateRequestUtils.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("UserControllerTest - Mock")
public class UserControllerTest extends ControllerTest {

    private static final String BASE_URL = "/api/user";

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
            assertSuccess(requestBuilder, status().isCreated());
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
            assertException(expectedError, requestBuilder);
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
            assertException(expectedError, requestBuilder);
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
            assertException(expectedError, requestBuilder);
        }

        private MockHttpServletRequestBuilder buildJoinRequest(UserCreateRequest request) throws JsonProcessingException {
            return MockMvcRequestBuilders
                    .post(BASE_URL)
                    .content(toJson(request))
                    .contentType(APPLICATION_JSON);
        }

    }

    @Test
    @DisplayName("회원탈퇴 API - 회원 탈퇴에 성공한다")
    void successDeleteUser() throws Exception {
        //when
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(BASE_URL)
                .header(AUTHORIZATION, BEARER + ACCESS_TOKEN);

        //then
        assertSuccess(requestBuilder, status().isOk());
    }

    @Test
    @DisplayName("회원 정보 수정 API - 정보 수정에 성공한다")
    void successUpdateUser() throws Exception {
        //given
        UserUpdateRequest request = UserUpdateRequestUtils.request(UserFixture.A);

        //when
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch(BASE_URL)
                .header(AUTHORIZATION, BEARER + ACCESS_TOKEN)
                .content(toJson(request))
                .contentType(APPLICATION_JSON);

        //then
        assertSuccess(requestBuilder, status().isOk());
    }

    private void assertSuccess(MockHttpServletRequestBuilder requestBuilder, ResultMatcher status) throws Exception {
        mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status,
                        jsonPath("$").exists(),
                        jsonPath("$.success").value(true)
                );
    }

    private void assertException(ErrorCode expectedError, MockHttpServletRequestBuilder requestBuilder) throws Exception {
        mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$").exists(),
                        jsonPath("$.message").value(expectedError.getMessage())
                );
    }
}


