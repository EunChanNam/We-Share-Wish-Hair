package com.inq.wishhair.wesharewishhair.global.mail.event;

import com.inq.wishhair.wesharewishhair.global.mail.dto.RefundMailDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class MailSendEvent {

    private final RefundMailDto mailDto;
}
