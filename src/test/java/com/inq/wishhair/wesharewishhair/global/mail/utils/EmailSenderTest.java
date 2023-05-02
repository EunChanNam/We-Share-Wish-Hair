package com.inq.wishhair.wesharewishhair.global.mail.utils;

import com.inq.wishhair.wesharewishhair.global.base.InfraTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@DisplayName("이메일 전송 유틸 테스트 - Mock Test")
public class EmailSenderTest extends InfraTest {
    private static final String AUTH_TEMPLATE = "AuthMailTemplate";
    private static final String REFUND_TEMPLATE = "RefundRequestMailTemplate";
    private static final String ADDRESS = "hello@naver.com";

    @Mock
    private JavaMailSender mailSender;
    @Mock
    private ITemplateEngine templateEngine;
    @Mock
    private MimeMessage mimeMessage;

    private EmailSender emailSender;

    @BeforeEach
    void setUp() {
        emailSender = new EmailSender(mailSender, templateEngine);
    }

    @Test
    @DisplayName("인증 메일 발송 테스트")
    void sendAuthMail() {
        //given
        given(templateEngine.process(any(String.class), any(Context.class))).willReturn(AUTH_TEMPLATE);
        given(mailSender.createMimeMessage()).willReturn(mimeMessage);

        //when, then
        assertDoesNotThrow(() -> emailSender.sendAuthMail(ADDRESS, "1927"));
    }
}
