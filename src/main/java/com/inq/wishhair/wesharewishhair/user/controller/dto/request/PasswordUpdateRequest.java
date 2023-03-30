package com.inq.wishhair.wesharewishhair.user.controller.dto.request;

import com.inq.wishhair.wesharewishhair.user.domain.Password;
import com.inq.wishhair.wesharewishhair.user.service.dto.PasswordUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PasswordUpdateRequest {

    private String oldPassword;

    private String newPassword;

    public PasswordUpdateDto toPasswordUpdateRequest() {
        return new PasswordUpdateDto(new Password(oldPassword), new Password(newPassword));
    }
}
