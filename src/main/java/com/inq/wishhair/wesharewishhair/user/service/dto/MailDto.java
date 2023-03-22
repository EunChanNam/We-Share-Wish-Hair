package com.inq.wishhair.wesharewishhair.user.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MailDto {

    private String address;
    private String title;
    private String content;

    public static MailDto of(String address, String title, String content) {
        return new MailDto(address, title, content);
    }
}
