package com.inq.wishhair.wesharewishhair.user.controller.dto.request;

import com.inq.wishhair.wesharewishhair.user.service.dto.MailDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MailRequest {

    @NotNull
    private String email;

    public MailDto toMailDto(String title, String content) {
        return MailDto.of(email, title, content, true);
    }
}
