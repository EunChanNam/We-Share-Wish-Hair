package com.inq.wishhair.wesharewishhair.user.service;

import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.global.mail.utils.EmailSender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class EmailSenderTest extends ServiceTest {

    @Autowired
    private EmailSender emailSender;

    @Value("${mail.receiver}")
    private String receiver;

    @Nested
    @DisplayName("이메일 발송 서비스")
    class sendEmail {
        @Test
        @DisplayName("만들어진 메일 정보를 통해 메일을 전송한다")
        void success() {
            //when, then -> 성공하면 성공적으로 메일이 발송된다.
            assertDoesNotThrow(() -> emailSender.sendAuthMail(receiver, "1234"));
        }
    }
}
