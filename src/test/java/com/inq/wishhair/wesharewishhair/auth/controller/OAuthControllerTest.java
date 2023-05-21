package com.inq.wishhair.wesharewishhair.auth.controller;

import com.inq.wishhair.wesharewishhair.auth.exception.WishHairOAuthException;
import com.inq.wishhair.wesharewishhair.auth.infra.oauth.dto.response.GoogleUserResponse;
import com.inq.wishhair.wesharewishhair.auth.infra.oauth.dto.response.OAuthUserResponse;
import com.inq.wishhair.wesharewishhair.auth.service.dto.response.LoginResponse;
import com.inq.wishhair.wesharewishhair.global.base.ControllerTest;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.enums.Tag;
import com.inq.wishhair.wesharewishhair.user.domain.FaceShape;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.inq.wishhair.wesharewishhair.global.utils.TokenUtils.ACCESS_TOKEN;
import static com.inq.wishhair.wesharewishhair.global.utils.TokenUtils.REFRESH_TOKEN;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("OAuthController Test - WebMvcTest")
public class OAuthControllerTest extends ControllerTest {

    private static final String BASE_URL = "/api/oauth";

    @Nested
    @DisplayName("OAuth Access Link 조회 API")
    class access {
        @Test
        @DisplayName("OAuth Access Link 를 성공적으로 조회한다")
        void success() throws Exception {
            //given
            given(uriGenerator.generate()).willReturn("https://accounts.google.com/o/oauth2/v2/auth?response_type=code&client_id=client-id&scope=email profile&redirect_uri=redirect-url");

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get(BASE_URL + "/access");

            //then
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk())
                    .andDo(
                            restDocs.document(
                                    responseFields(
                                            fieldWithPath("result").description("OAuth 인증 URI")
                                    )
                            )
                    );
        }
    }

    @Nested
    @DisplayName("OAuth 로그인 API")
    class oauthLogin {
        @Test
        @DisplayName("OAuth 로그인에 성공한다")
        void success() throws Exception {
            //given
            LoginResponse loginResponse = generateLoginResponse();
            given(oauthService.login("code")).willReturn(loginResponse);

            //when
            MockHttpServletRequestBuilder requestBuilder = generateLoginRequest();

            //then
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk())
                    .andDo(
                            restDocs.document(
                                    requestParameters(
                                            parameterWithName("authorizationCode").description("oauth 인증 코드")
                                    ),
                                    responseFields(
                                            fieldWithPath("userInfo.nickname").description("사용자 닉네임"),
                                            fieldWithPath("userInfo.hasFaceShape").description("얼굴형 보유 여부"),
                                            fieldWithPath("userInfo.faceShapeTag").description("사용자 얼굴형, 얼굴형을 보유하지 않을 시 null"),
                                            fieldWithPath("accessToken").description("Access 토큰 (Expire 2시간)"),
                                            fieldWithPath("refreshToken").description("Refresh 토큰 (Expire 2주)")
                                    )
                            )
                    );
        }

        @Test
        @DisplayName("잘못된 authorizationCode 나 Google Server 통신간 오류로 실패한다")
        void failByGoogleServer() throws Exception {
            //given
            ErrorCode expectedError = ErrorCode.GOOGLE_OAUTH_EXCEPTION;
            given(oauthService.login("code")).willThrow(new WishHairException(expectedError));

            //when
            MockHttpServletRequestBuilder requestBuilder = generateLoginRequest();

            //then
            assertException(expectedError, requestBuilder, status().isForbidden());
        }

        @Test
        @DisplayName("존재하지 않는 회원으로 회원가입 정보를 응답하며 실패한다")
        void failByNoSignUp() throws Exception {
            //given
            OAuthUserResponse oauthUserResponse = new GoogleUserResponse(UserFixture.A.getName(), UserFixture.A.getEmail());
            given(oauthService.login("code")).willThrow(new WishHairOAuthException(oauthUserResponse));

            //when
            MockHttpServletRequestBuilder requestBuilder = generateLoginRequest();

            //then
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isNotFound())
                    .andDo(
                            restDocs.document(
                                    responseFields(
                                            fieldWithPath("name").description("사용자 이름"),
                                            fieldWithPath("email").description("사용자 이메일")
                                    )
                            )
                    );
        }

        private MockHttpServletRequestBuilder generateLoginRequest() {
            return MockMvcRequestBuilders
                    .get(BASE_URL + "/login?authorizationCode=code");
        }

        private LoginResponse generateLoginResponse() {
            User user = UserFixture.A.toEntity();
            user.updateFaceShape(new FaceShape(Tag.OBLONG));
            return new LoginResponse(user, ACCESS_TOKEN, REFRESH_TOKEN);
        }
    }
}
