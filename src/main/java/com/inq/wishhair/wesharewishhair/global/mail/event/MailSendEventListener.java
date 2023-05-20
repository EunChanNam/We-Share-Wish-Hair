package com.inq.wishhair.wesharewishhair.global.mail.event;

import com.inq.wishhair.wesharewishhair.auth.event.AuthMailSendEvent;
import com.inq.wishhair.wesharewishhair.global.mail.utils.EmailSender;
import com.inq.wishhair.wesharewishhair.user.event.RefundMailSendEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class MailSendEventListener {

    private final EmailSender emailSender;

    @Async("mailAsyncExecutor")
    @EventListener
    public void sendAuthMail(AuthMailSendEvent event) throws Exception {
        emailSender.sendAuthMail(event.email().getValue(), event.authKey());
    }

    @Async("mailAsyncExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendRefundMail(RefundMailSendEvent event) throws Exception {
        emailSender.sendRefundRequestMail(event.dto());
    }
}
