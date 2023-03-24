package com.inq.wishhair.wesharewishhair.user.controller;

import com.inq.wishhair.wesharewishhair.global.base.ControllerTest;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.MailRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MailControllerTest extends ControllerTest {
    private static final String WRONG_EMAIL = "email@navercom";
    private static final String EMAIL = "email@naver.com";
    private static final String SEND_URL = "/api/email/send";

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
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .post(SEND_URL)
                    .param("email", request.getEmail());

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
