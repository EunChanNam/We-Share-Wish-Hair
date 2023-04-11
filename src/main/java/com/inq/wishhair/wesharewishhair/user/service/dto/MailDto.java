package com.inq.wishhair.wesharewishhair.user.service.dto;

import com.inq.wishhair.wesharewishhair.user.domain.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MailDto {

    private Email email;
    private String title;
    private String content;
    private boolean requireValidate;

    public static MailDto of(Email email, String title, String content) {
        return new MailDto(email, title, content, false);
    }

    public static MailDto of(String address, String title, String content, boolean requireValidate) {
        return new MailDto(new Email(address), title, content, requireValidate);
    }
}
