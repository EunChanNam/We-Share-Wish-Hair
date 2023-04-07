package com.inq.wishhair.wesharewishhair.user.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PasswordUpdateRequest {

    private String oldPassword;

    private String newPassword;
}
