package com.inq.wishhair.wesharewishhair.global.fixture;

import com.inq.wishhair.wesharewishhair.user.service.dto.MailDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MailDtoFixture {
    A("namhm23@naver.com", "2839");

    private final String email;
    private final String title = "We-Share-Wish-Hair 이메일 인증";
    private final String content;

    public MailDto toMailDto() {
        return MailDto.of(email, title, content);
    }
}
