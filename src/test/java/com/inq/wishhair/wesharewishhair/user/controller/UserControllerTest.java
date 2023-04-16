package com.inq.wishhair.wesharewishhair.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.global.base.ControllerTest;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.PasswordUpdateRequest;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.UserCreateRequest;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.UserUpdateRequest;
import com.inq.wishhair.wesharewishhair.user.controller.utils.PasswordUpdateRequestUtils;
import com.inq.wishhair.wesharewishhair.user.controller.utils.UserUpdateRequestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.inq.wishhair.wesharewishhair.global.utils.TokenUtils.*;
import static com.inq.wishhair.wesharewishhair.user.controller.utils.UserCreateRequestUtils.*;
import static org.mockito.ArgumentMatchers.any;
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
            given(userService.createUser(any())).willReturn(1L);

            //when
            MockHttpServletRequestBuilder requestBuilder = buildJoinRequest(request);

            //then
            assertSuccess(requestBuilder, status().isCreated());
        }

        @Test
        @DisplayName("중복된 닉네임으로 400 예외를 던진다")
        void failByDuplicatedNickname() throws Exception {
            //given
            UserCreateRequest request = successRequest();
            ErrorCode expectedError = ErrorCode.USER_DUPLICATED_NICKNAME;
            given(userService.createUser(any()))
                    .willThrow(new WishHairException(expectedError));

            //when
            MockHttpServletRequestBuilder requestBuilder = buildJoinRequest(request);

            //then
            assertException(expectedError, requestBuilder, status().isBadRequest());
        }

        private MockHttpServletRequestBuilder buildJoinRequest(UserCreateRequest request) throws JsonProcessingException {
            return MockMvcRequestBuilders
                    .post(BASE_URL)
                    .content(toJson(request))
                    .contentType(APPLICATION_JSON);
        }

    }

    @Nested
    @DisplayName("회원탈퇴 API 테스트")
    class deleteUser {
        @Test
        @DisplayName("헤더에 토큰을 포함하지 않아 예외를 던진다")
        void failByNoAccessToken() throws Exception {
            //given
            ErrorCode expectedError = ErrorCode.AUTH_REQUIRED_LOGIN;

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .delete(BASE_URL);

            //then
            assertException(expectedError, requestBuilder, status().isUnauthorized());
        }

        @Test
        @DisplayName("회원탈퇴를 한다")
        void success() throws Exception {
            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .delete(BASE_URL)
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN);

            //then
            assertSuccess(requestBuilder, status().isOk());
        }
    }

    @Nested
    @DisplayName("회원 정보 수정 API 테스트")
    class updateUser {
        @Test
        @DisplayName("헤더에 토큰을 포함하지 않아 예외를 던진다")
        void failByNoAccessToken() throws Exception {
            //given
            UserUpdateRequest request = UserUpdateRequestUtils.request(UserFixture.A);
            ErrorCode expectedError = ErrorCode.AUTH_REQUIRED_LOGIN;

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .patch(BASE_URL)
                    .content(toJson(request))
                    .contentType(APPLICATION_JSON);

            //then
            assertException(expectedError, requestBuilder, status().isUnauthorized());
        }

        @Test
        @DisplayName("회원 정보 수정을 한다")
        void success() throws Exception {
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
    }

    @Nested
    @DisplayName("비밀번호 변경 API 테스트")
    class updatePassword {
        @Test
        @DisplayName("헤더에 토큰을 포함하지 않아 예외를 던진다")
        void failByNoAccessToken() throws Exception {
            //given
            PasswordUpdateRequest request = PasswordUpdateRequestUtils.request(UserFixture.A);
            ErrorCode expectedError = ErrorCode.AUTH_REQUIRED_LOGIN;

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .patch(BASE_URL + "/password")
                    .content(toJson(request))
                    .contentType(APPLICATION_JSON);

            //then
            assertException(expectedError, requestBuilder, status().isUnauthorized());
        }

        @Test
        @DisplayName("비밀번호 변경을 한다")
        void successUpdatePassword() throws Exception {
            //given
            PasswordUpdateRequest request = PasswordUpdateRequestUtils.request(UserFixture.A);

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .patch(BASE_URL + "/password")
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN)
                    .content(toJson(request))
                    .contentType(APPLICATION_JSON);

            //then
            assertSuccess(requestBuilder, status().isOk());
        }
    }

}


