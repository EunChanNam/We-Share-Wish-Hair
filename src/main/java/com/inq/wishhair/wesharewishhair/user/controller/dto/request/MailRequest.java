package com.inq.wishhair.wesharewishhair.user.controller.dto.request;

import com.inq.wishhair.wesharewishhair.user.service.dto.MailDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MailRequest {

    private String email;

    public MailDto toMailDto(String title, String content) {
        return MailDto.of(email, title, content);
    }
}
