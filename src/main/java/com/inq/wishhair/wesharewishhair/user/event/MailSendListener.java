package com.inq.wishhair.wesharewishhair.user.event;

import com.inq.wishhair.wesharewishhair.global.event.MailSendEvent;
import com.inq.wishhair.wesharewishhair.user.service.MailSendService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailSendListener {

    private final MailSendService mailSendService;

    @EventListener
    public void sendMail(MailSendEvent event) {
        mailSendService.sendMail(event.getMailDto());
    }
}
