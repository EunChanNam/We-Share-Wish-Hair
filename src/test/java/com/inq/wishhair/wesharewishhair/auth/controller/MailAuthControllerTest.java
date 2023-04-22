package com.inq.wishhair.wesharewishhair.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.inq.wishhair.wesharewishhair.global.base.ControllerTest;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.AuthKeyRequest;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.MailRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MailAuthControllerTest extends ControllerTest {

    @Value("${mail.receiver}")
    private String receiver;
    private static final String WRONG_EMAIL = "email@navercom";
    private static final String AUTH_KEY = "KEY";

    private static final String SEND_URL = "/api/email/send";
    private static final String VALIDATE_URL = "/api/email/validate";

    @Nested
    @DisplayName("메일 전송 API")
    class sendAuthorizationMail {
        @Test
        @DisplayName("올바르지 않은 형식의 이메일로 400 예외를 던진다")
        void failByWrongEmail() throws Exception {
            //given
            MailRequest request = new MailRequest(WRONG_EMAIL);
            ErrorCode expectedError = ErrorCode.USER_INVALID_EMAIL;

            //when
            MockHttpServletRequestBuilder requestBuilder = generateMailSendRequest(request);

            //then
            assertException(expectedError, requestBuilder, status().isBadRequest());
        }

        @Test
        @DisplayName("성공적으로 메일을 전송한다")
        void success() throws Exception {
            //given
            MailRequest request = new MailRequest(receiver);

            //when
            MockHttpServletRequestBuilder requestBuilder = generateMailSendRequest(request);

            //then
            mockMvc.perform(requestBuilder)
                    .andExpectAll(
                            status().isOk(),
                            jsonPath("$").exists()
                    ).andDo(
                            restDocs.document(
                                    requestFields(
                                            fieldWithPath("email").description("이메일 (아이디)")
                                                    .attributes(constraint("이메일 형식 준수"))
                                    ),
                                    responseFields(
                                            fieldWithPath("sessionId").description("Session ID 유효기간 : 5분")
                                    )
                            )
                    );
        }

        @Test
        @DisplayName("중복된 이메일로 실패한다")
        void failByDuplicatedEmail() throws Exception {
            //given
            MailRequest request = new MailRequest(receiver);
            ErrorCode expectedError = ErrorCode.USER_DUPLICATED_EMAIL;
            doThrow(new WishHairException(expectedError)).when(userValidator).validateEmailIsNotDuplicated(any());

            //when
            MockHttpServletRequestBuilder requestBuilder = generateMailSendRequest(request);

            //then
            assertException(expectedError, requestBuilder, status().isConflict());
        }

        private MockHttpServletRequestBuilder generateMailSendRequest(MailRequest request) throws JsonProcessingException {
            return MockMvcRequestBuilders
                    .post(SEND_URL)
                    .content(toJson(request))
                    .contentType(MediaType.APPLICATION_JSON);
        }
    }

    @Nested
    @DisplayName("메일 검증 API")
    class authorizeKey {
        @Test
        @DisplayName("잘못된 인증 키로 검증에 실패한다")
        void failByAuthKey() throws Exception {
            //given
            AuthKeyRequest request = new AuthKeyRequest("1111");
            MockHttpSession session = new MockHttpSession();
            session.setAttribute(AUTH_KEY, "2222");

            ErrorCode expectedError = ErrorCode.MAIL_INVALID_KEY;
            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .post(VALIDATE_URL)
                    .content(toJson(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .session(session);

            //then
            assertException(expectedError, requestBuilder, status().isUnauthorized());
        }

        @Test
        @DisplayName("인증키가 만료되어 검증에 실패한다")
        void failByExpired() throws Exception {
            //given
            AuthKeyRequest request = new AuthKeyRequest("1111");

            ErrorCode expectedError = ErrorCode.MAIL_EXPIRED_KEY;

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .post(VALIDATE_URL)
                    .content(toJson(request))
                    .contentType(MediaType.APPLICATION_JSON);

            //then
            assertException(expectedError, requestBuilder, status().isUnauthorized());
        }

        @Test
        @DisplayName("올바른 인증키로 검증에 성공한다")
        void success() throws Exception {
            //given
            AuthKeyRequest request = new AuthKeyRequest("1111");
            MockHttpSession session = new MockHttpSession();
            session.setAttribute(AUTH_KEY, "1111");

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .post(VALIDATE_URL)
                    .content(toJson(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .session(session);

            //then
            mockMvc.perform(requestBuilder)
                    .andExpectAll(
                            status().isOk(),
                            jsonPath("$").exists()
                    ).andDo(
                            restDocs.document(
                                    requestFields(
                                            fieldWithPath("authKey").description("사용자 입력 인증 키")
                                                    .attributes(constraint("4자리 숫자"))
                                    ),
                                    successResponseDocument()
                            )
                    );
        }
    }
}
