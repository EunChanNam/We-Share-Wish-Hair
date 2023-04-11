package com.inq.wishhair.wesharewishhair.user.event;

import com.inq.wishhair.wesharewishhair.global.event.MailSendEvent;
import com.inq.wishhair.wesharewishhair.user.service.MailSendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class MailSendListener {

    private final MailSendService mailSendService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendMail(MailSendEvent event) {
        mailSendService.sendMail(event.getMailDto());
    }
}
