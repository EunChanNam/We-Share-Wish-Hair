package com.inq.wishhair.wesharewishhair.user.event;

import com.inq.wishhair.wesharewishhair.user.service.MailSendService;
import com.inq.wishhair.wesharewishhair.user.service.dto.MailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailSendListener {

    private final MailSendService mailSendService;

    @EventListener
    public void sendMail(MailDto mailDto) {
        mailSendService.sendMail(mailDto);
    }
}
