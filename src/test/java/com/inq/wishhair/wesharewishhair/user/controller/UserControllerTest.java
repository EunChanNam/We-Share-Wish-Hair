package com.inq.wishhair.wesharewishhair.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.global.base.ControllerTest;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.enums.Tag;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.FaceShapeUpdateRequest;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.PasswordUpdateRequest;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.SignUpRequest;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.UserUpdateRequest;
import com.inq.wishhair.wesharewishhair.user.controller.utils.PasswordUpdateRequestUtils;
import com.inq.wishhair.wesharewishhair.user.controller.utils.UserUpdateRequestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.inq.wishhair.wesharewishhair.global.utils.TokenUtils.*;
import static com.inq.wishhair.wesharewishhair.user.controller.utils.SignUpRequestUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
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
            assertException(expectedError, requestBuilder, status().isBadRequest());
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
        @DisplayName("중복된 닉네임으로 수정에 실패한다")
        void failByDuplicatedNickname() throws Exception {
            UserUpdateRequest request = UserUpdateRequestUtils.request(UserFixture.A);
            ErrorCode expectedError = ErrorCode.USER_DUPLICATED_NICKNAME;
            doThrow(new WishHairException(expectedError)).when(userService).updateUser(any(), any());

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .patch(BASE_URL)
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN)
                    .content(toJson(request))
                    .contentType(APPLICATION_JSON);

            //then
            assertException(expectedError, requestBuilder, status().isBadRequest());
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
        @DisplayName("기존 비밀번호가 일치하지 않아 실패한다")
        void failByOldPassword() throws Exception {
            //given
            PasswordUpdateRequest request = PasswordUpdateRequestUtils.request(UserFixture.A);
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
            PasswordUpdateRequest request = PasswordUpdateRequestUtils.request(UserFixture.A);

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
            FaceShapeUpdateRequest request = new FaceShapeUpdateRequest(Tag.OBLONG);
            ErrorCode expectedError = ErrorCode.AUTH_REQUIRED_LOGIN;

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .patch(BASE_URL + "/face_shape")
                    .contentType(APPLICATION_JSON)
                    .content(toJson(request));

            //then
            assertException(expectedError, requestBuilder, status().isUnauthorized());
        }

        @Test
        @DisplayName("사용자 얼굴형을 업데이트 한다")
        void success() throws Exception {
            //given
            FaceShapeUpdateRequest request = new FaceShapeUpdateRequest(Tag.OBLONG);

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .patch(BASE_URL + "/face_shape")
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN)
                    .contentType(APPLICATION_JSON)
                    .content(toJson(request));

            //then
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk())
                    .andDo(
                            restDocs.document(
                                    accessTokenHeaderDocument(),
                                    requestFields(
                                            fieldWithPath("faceShapeTag").description("얼굴형 태그")
                                                    .attributes(constraint("반드시 존재하는 얼굴형 태그"))
                                    ),
                                    successResponseDocument()
                            )
                    );
        }
    }
}


