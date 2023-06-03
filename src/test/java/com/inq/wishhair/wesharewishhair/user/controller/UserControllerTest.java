package com.inq.wishhair.wesharewishhair.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.inq.wishhair.wesharewishhair.global.dto.response.SimpleResponseWrapper;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.global.base.ControllerTest;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.Tag;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.*;
import com.inq.wishhair.wesharewishhair.user.controller.utils.FaceShapeUpdateRequestUtils;
import com.inq.wishhair.wesharewishhair.user.controller.utils.PasswordUpdateRequestUtils;
import com.inq.wishhair.wesharewishhair.user.controller.utils.UserUpdateRequestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static com.inq.wishhair.wesharewishhair.global.fixture.UserFixture.*;
import static com.inq.wishhair.wesharewishhair.global.utils.TokenUtils.*;
import static com.inq.wishhair.wesharewishhair.user.controller.utils.SignUpRequestUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("UserControllerTest - Mock")
public class UserControllerTest extends ControllerTest {

    private static final String BASE_URL = "/api/users";

    @Nested
    @DisplayName("회원가입 API 테스트")
    class createUser {
        @Test
        @DisplayName("성공적인 회원가입 테스트")
        void success() throws Exception {
            //given
            SignUpRequest request = successRequest();
            given(userService.createUser(any())).willReturn(1L);

            //when
            MockHttpServletRequestBuilder requestBuilder = buildJoinRequest(request);

            //then
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isCreated())
                    .andDo(
                            restDocs.document(
                                    requestFields(
                                            fieldWithPath("email").description("이메일 (아이디)")
                                                    .attributes(constraint("이메일 형식 준수")),
                                            fieldWithPath("pw").description("비밀 번호")
                                                    .attributes(constraint("영어,숫자,특수문자 조합 8~20자")),
                                            fieldWithPath("name").description("사용자 실명"),
                                            fieldWithPath("nickname").description("닉네임")
                                                    .attributes(constraint("영어,숫자,한글 조합 2~8자 (공백 불가능)")),
                                            fieldWithPath("sex").description("사용자 성별")
                                    ),
                                    successResponseDocument()
                            )
                    );
        }

        @Test
        @DisplayName("중복된 닉네임으로 400 예외를 던진다")
        void failByDuplicatedNickname() throws Exception {
            //given
            SignUpRequest request = successRequest();
            ErrorCode expectedError = ErrorCode.USER_DUPLICATED_NICKNAME;
            given(userService.createUser(any()))
                    .willThrow(new WishHairException(expectedError));

            //when
            MockHttpServletRequestBuilder requestBuilder = buildJoinRequest(request);

            //then
            assertException(expectedError, requestBuilder, status().isConflict());
        }

        private MockHttpServletRequestBuilder buildJoinRequest(SignUpRequest request) throws JsonProcessingException {
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
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk())
                    .andDo(
                            restDocs.document(
                                    accessTokenHeaderDocument(),
                                    successResponseDocument()
                            )
                    );
        }
    }

    @Nested
    @DisplayName("회원 정보 수정 API 테스트")
    class updateUser {
        @Test
        @DisplayName("헤더에 토큰을 포함하지 않아 예외를 던진다")
        void failByNoAccessToken() throws Exception {
            //given
            UserUpdateRequest request = UserUpdateRequestUtils.request(A);
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
        @DisplayName("중복된 닉네임으로 수정에 실패한다")
        void failByDuplicatedNickname() throws Exception {
            UserUpdateRequest request = UserUpdateRequestUtils.request(A);
            ErrorCode expectedError = ErrorCode.USER_DUPLICATED_NICKNAME;
            doThrow(new WishHairException(expectedError)).when(userService).updateUser(any(), any());

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .patch(BASE_URL)
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN)
                    .content(toJson(request))
                    .contentType(APPLICATION_JSON);

            //then
            assertException(expectedError, requestBuilder, status().isConflict());
        }

        @Test
        @DisplayName("회원 정보 수정을 한다")
        void success() throws Exception {
            //given
            UserUpdateRequest request = UserUpdateRequestUtils.request(A);

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .patch(BASE_URL)
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN)
                    .content(toJson(request))
                    .contentType(APPLICATION_JSON);

            //then
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk())
                    .andDo(
                            restDocs.document(
                                    accessTokenHeaderDocument(),
                                    requestFields(
                                            fieldWithPath("nickname").description("변경할 닉네임")
                                                    .attributes(constraint("영어,숫자,한글 조합 2~8자 (공백 불가능)")),
                                            fieldWithPath("sex").description("변경할 성별")
                                    ),
                                    successResponseDocument()
                            )
                    );
        }
    }

    @Nested
    @DisplayName("비밀번호 변경 API 테스트")
    class updatePassword {
        @Test
        @DisplayName("헤더에 토큰을 포함하지 않아 예외를 던진다")
        void failByNoAccessToken() throws Exception {
            //given
            PasswordUpdateRequest request = PasswordUpdateRequestUtils.request(A);
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
        @DisplayName("기존 비밀번호가 일치하지 않아 실패한다")
        void failByOldPassword() throws Exception {
            //given
            PasswordUpdateRequest request = PasswordUpdateRequestUtils.request(A);
            ErrorCode expectedError = ErrorCode.USER_WRONG_PASSWORD;
            doThrow(new WishHairException(expectedError)).when(userService).updatePassword(any(), any());

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .patch(BASE_URL + "/password")
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN)
                    .content(toJson(request))
                    .contentType(APPLICATION_JSON);

            //then
            assertException(expectedError, requestBuilder, status().isBadRequest());
        }

        @Test
        @DisplayName("비밀번호 변경을 한다")
        void successUpdatePassword() throws Exception {
            //given
            PasswordUpdateRequest request = PasswordUpdateRequestUtils.request(A);

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .patch(BASE_URL + "/password")
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN)
                    .content(toJson(request))
                    .contentType(APPLICATION_JSON);

            //then
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk())
                    .andDo(
                            restDocs.document(
                                    accessTokenHeaderDocument(),
                                    requestFields(
                                            fieldWithPath("oldPassword").description("기존 비밀번호")
                                                    .attributes(constraint("기존 비밀번호와 같아야함")),
                                            fieldWithPath("newPassword").description("새로운 비밀번호")
                                                    .attributes(constraint("영어,숫자,특수문자 조합 8~20자"))
                                    ),
                                    successResponseDocument()
                            )
                    );
        }
    }

    @Nested
    @DisplayName("사용자 얼굴형 업데이트 API")
    class updateFaceShape {
        @Test
        @DisplayName("헤더에 토큰을 포함하지 않아 실패")
        void failByNoAccessToken() throws Exception {
            //given
            FaceShapeUpdateRequest request = FaceShapeUpdateRequestUtils.request();
            ErrorCode expectedError = ErrorCode.AUTH_REQUIRED_LOGIN;

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .multipart(HttpMethod.PATCH, BASE_URL + "/face_shape")
                    .file((MockMultipartFile) request.getFile());

            //then
            assertException(expectedError, requestBuilder, status().isUnauthorized());
        }

        @Test
        @DisplayName("사용자 얼굴형을 분석 후 업데이트 한다")
        void success() throws Exception {
            //given
            FaceShapeUpdateRequest request = FaceShapeUpdateRequestUtils.request();
            SimpleResponseWrapper<String> response = new SimpleResponseWrapper<>(Tag.OBLONG.getDescription());
            given(userService.updateFaceShape(1L, request.getFile())).willReturn(response);

            //when
            MockHttpServletRequestBuilder requestBuilder = generateUpdateFaceShapeRequest(request);

            //then
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk())
                    .andDo(
                            restDocs.document(
                                    accessTokenHeaderDocument(),
                                    requestParts(
                                            partWithName("file").description("분석할 사용자 사진")
                                                    .attributes(constraint("필수 입니다"))
                                    ),
                                    responseFields(
                                            fieldWithPath("result").description("분석한 사용자 얼굴형")
                                    )
                            )
                    );
        }

        @Test
        @DisplayName("빈 파일을 입력해 실패한다")
        void failByEmptyFile() throws Exception {
            //given
            FaceShapeUpdateRequest request = FaceShapeUpdateRequestUtils.request();
            ErrorCode expectedError = ErrorCode.EMPTY_FILE_EX;
            given(userService.updateFaceShape(1L, request.getFile()))
                    .willThrow(new WishHairException(expectedError));

            //when
            MockHttpServletRequestBuilder requestBuilder = generateUpdateFaceShapeRequest(request);

            //then
            assertException(expectedError, requestBuilder, status().isBadRequest());
        }

        @Test
        @DisplayName("Flask Server 오류로 실패한다")
        void failByFlaskServer() throws Exception {
            //given
            FaceShapeUpdateRequest request = FaceShapeUpdateRequestUtils.request();
            ErrorCode expectedError = ErrorCode.FLASK_SERVER_EXCEPTION;
            given(userService.updateFaceShape(1L, request.getFile()))
                    .willThrow(new WishHairException(expectedError));

            //when
            MockHttpServletRequestBuilder requestBuilder = generateUpdateFaceShapeRequest(request);

            //then
            assertException(expectedError, requestBuilder, status().isInternalServerError());
        }

        @Test
        @DisplayName("Flask Server 의 잘못된 응답값으로 실패한다")
        void failByInvalidResponse() throws Exception {
            FaceShapeUpdateRequest request = FaceShapeUpdateRequestUtils.request();
            ErrorCode expectedError = ErrorCode.FLASK_RESPONSE_ERROR;
            given(userService.updateFaceShape(1L, request.getFile()))
                    .willThrow(new WishHairException(expectedError));

            //when
            MockHttpServletRequestBuilder requestBuilder = generateUpdateFaceShapeRequest(request);

            //then
            assertException(expectedError, requestBuilder, status().isInternalServerError());
        }

        private MockHttpServletRequestBuilder generateUpdateFaceShapeRequest(FaceShapeUpdateRequest request) {
            return MockMvcRequestBuilders
                    .multipart(HttpMethod.PATCH, BASE_URL + "/face_shape")
                    .file((MockMultipartFile) request.getFile())
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN);
        }
    }

    @Nested
    @DisplayName("비밀번호 갱신 API")
    class refreshPassword {
        private static final String REFRESH_URL = "/refresh/password";
        private static final String NEW_PASSWORD = "hello1234@";
        private final PasswordRefreshRequest request = new PasswordRefreshRequest(A.getEmail(), NEW_PASSWORD);

        @Test
        @DisplayName("사용자 비밀번호를 갱신한다")
        void success() throws Exception {
            //when
            MockHttpServletRequestBuilder requestBuilder = generatePasswordRefreshRequest();

            //then
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk())
                    .andDo(
                            restDocs.document(
                                    requestFields(
                                            fieldWithPath("email").description("비밀번호 갱신할 사용자 이메일"),
                                            fieldWithPath("newPassword").description("새로 갱신할 비밀번호")
                                                    .attributes(constraint("비밀번호 형식 준수"))
                                    ),
                                    successResponseDocument()
                            )
                    );
        }

        @Test
        @DisplayName("틀린 비밀번호 형식으로 실패")
        void failByWongPassword() throws Exception {
            //given
            ErrorCode expectedError = ErrorCode.USER_INVALID_PASSWORD;

            doThrow(new WishHairException(expectedError))
                    .when(userService).refreshPassword(any(PasswordRefreshRequest.class));

            //when
            MockHttpServletRequestBuilder requestBuilder = generatePasswordRefreshRequest();

            //then
            assertException(expectedError, requestBuilder, status().isBadRequest());
        }

        @Test
        @DisplayName("입력받은 이메일에 해당하는 사용자가 없어 실패")
        void failByNotExistUser() throws Exception {
            //given
            ErrorCode expectedError = ErrorCode.USER_NOT_FOUND_BY_EMAIL;

            doThrow(new WishHairException(expectedError))
                    .when(userService).refreshPassword(any(PasswordRefreshRequest.class));

            //when
            MockHttpServletRequestBuilder requestBuilder = generatePasswordRefreshRequest();

            //then
            assertException(expectedError, requestBuilder, status().isNotFound());
        }

        private MockHttpServletRequestBuilder generatePasswordRefreshRequest() throws JsonProcessingException {
            return MockMvcRequestBuilders
                    .patch(BASE_URL + REFRESH_URL)
                    .contentType(APPLICATION_JSON)
                    .content(toJson(request));
        }
    }
}


