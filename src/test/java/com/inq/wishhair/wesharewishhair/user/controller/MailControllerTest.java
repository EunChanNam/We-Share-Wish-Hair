package com.inq.wishhair.wesharewishhair.user.controller;

import com.inq.wishhair.wesharewishhair.global.base.ControllerTest;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.AuthKeyRequest;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.MailRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MailControllerTest extends ControllerTest {
    private static final String WRONG_EMAIL = "email@navercom";
    private static final String EMAIL = "email@naver.com";
    private static final String AUTH_KEY = "KEY";

    private static final String SEND_URL = "/api/email/send";
    private static final String VALIDATE_URL = "/api/email/validate";

    @Nested
    @DisplayName("메일 전송 API")
    class sendAuthorizationMail {
        @Test
        @DisplayName("올바르지 않은 형식의 이메일로 400 예외를 던진다")
        void test1() throws Exception {
            //given
            MailRequest request = new MailRequest(WRONG_EMAIL);
            ErrorCode expectedError = ErrorCode.USER_INVALID_EMAIL;

            //when
            MockHttpServletRequestBuilder requestBuilder = generateMailSendRequest(request);

            //then
            mockMvc.perform(requestBuilder)
                    .andExpectAll(
                            status().isBadRequest(),
                            jsonPath("$").exists(),
                            jsonPath("$.message").value(expectedError.getMessage())
                    );
        }

        @Test
        @DisplayName("성공적으로 메일을 전송한다")
        void test2() throws Exception {
            //given
            MailRequest request = new MailRequest(EMAIL);

            //when
            MockHttpServletRequestBuilder requestBuilder = generateMailSendRequest(request);

            //then
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isNoContent());
        }

        private MockHttpServletRequestBuilder generateMailSendRequest(MailRequest request) {
            return MockMvcRequestBuilders
                    .post(SEND_URL)
                    .param("email", request.getEmail());
        }
    }

    @Nested
    @DisplayName("메일 검증 API")
    class authorizeKey {
        @Test
        @DisplayName("잘못된 인증 키로 검증에 실패한다")
        void test3() throws Exception {
            //given
            AuthKeyRequest request = new AuthKeyRequest("1111");
            MockHttpSession session = new MockHttpSession();
            session.setAttribute(AUTH_KEY, "2222");

            ErrorCode expectedError = ErrorCode.MAIL_INVALID_KEY;
            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .post(VALIDATE_URL)
                    .param("authKey", request.getAuthKey())
                    .session(session);

            //then
            mockMvc.perform(requestBuilder)
                    .andExpectAll(
                            status().isUnauthorized(),
                            jsonPath("$").exists(),
                            jsonPath("$.message").value(expectedError.getMessage())
                    );
        }

        @Test
        @DisplayName("인증키가 만료되어 검증에 실패한다")
        void test4() throws Exception {
            //given
            AuthKeyRequest request = new AuthKeyRequest("1111");

            ErrorCode expectedError = ErrorCode.MAIL_EXPIRED_KEY;

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .post(VALIDATE_URL)
                    .param("authKey", request.getAuthKey());

            //then
            mockMvc.perform(requestBuilder)
                    .andExpectAll(
                            status().isUnauthorized(),
                            jsonPath("$").exists(),
                            jsonPath("$.message").value(expectedError.getMessage())
                    );
        }
    }
}
