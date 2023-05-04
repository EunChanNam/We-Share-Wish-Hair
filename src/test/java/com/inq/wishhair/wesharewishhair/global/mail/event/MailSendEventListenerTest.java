package com.inq.wishhair.wesharewishhair.global.mail.event;

import com.inq.wishhair.wesharewishhair.auth.event.AuthMailSendEvent;
import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.global.mail.dto.RefundMailDto;
import com.inq.wishhair.wesharewishhair.global.mail.utils.EmailSender;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.PointUseRequest;
import com.inq.wishhair.wesharewishhair.user.controller.utils.PointUseRequestUtils;
import com.inq.wishhair.wesharewishhair.user.event.RefundMailSendEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
public class MailSendEventListenerTest {
    private static final String ADDRESS = "hello@naver.com";

    @Autowired
    private MailSendEventListener listener;

    @MockBean
    private EmailSender emailSender;

    @Test
    @DisplayName("인증 메일 발송 이벤트 리스너 테스트")
    void sendAuthMail() throws Exception {
        //given
        final String authKey = "2816";
        doNothing().when(emailSender).sendAuthMail(ADDRESS, authKey);

        //when, then
        assertDoesNotThrow(() -> listener.sendAuthMail(new AuthMailSendEvent(ADDRESS, authKey)));
    }

    @Test
    @DisplayName("포인트 환급요청 메일 발송 이벤트 리스너 테스틐")
    void sendRefundMail() throws Exception {
        //given
        PointUseRequest request = PointUseRequestUtils.request(1000);
        RefundMailDto refundMailDto = request.toRefundMailDto(UserFixture.A.toEntity());
        doNothing().when(emailSender).sendRefundRequestMail(refundMailDto);

        //when, then
        assertDoesNotThrow(() -> listener.sendRefundMail(new RefundMailSendEvent(refundMailDto)));
    }
}
