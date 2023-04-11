package com.inq.wishhair.wesharewishhair.user.service.utils;

import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.user.domain.Email;
import com.inq.wishhair.wesharewishhair.user.service.dto.MailDto;

public abstract class MailDtoUtils {

    private static final String TITLE = "We-Share-Wish-Hair 테스트 이메일 인증";
    private static final String CONTENTS = "2839";

    public static MailDto mailDto(UserFixture fixture) {
        return new MailDto(new Email(fixture.getEmail()), TITLE, CONTENTS);
    }
}
