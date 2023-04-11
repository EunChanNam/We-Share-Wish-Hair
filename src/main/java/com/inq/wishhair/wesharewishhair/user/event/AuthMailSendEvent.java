package com.inq.wishhair.wesharewishhair.user.event;

import com.inq.wishhair.wesharewishhair.global.event.MailSendEvent;
import com.inq.wishhair.wesharewishhair.user.service.dto.MailDto;

public class AuthMailSendEvent extends MailSendEvent {

    private static final String MAIL_TITLE = "We-Share-Wish-Hair 이메일 인증";

    public AuthMailSendEvent(String address, String contents) {
        super(MailDto.of(address, MAIL_TITLE, contents, true));
    }
}
