package com.inq.wishhair.wesharewishhair.user.event;

import com.inq.wishhair.wesharewishhair.user.service.dto.MailDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RefundMailSendEvent {

    private final MailDto mailDto;
}
