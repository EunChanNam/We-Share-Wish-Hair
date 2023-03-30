package com.inq.wishhair.wesharewishhair.user.service.dto;

import com.inq.wishhair.wesharewishhair.user.domain.Password;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PasswordUpdateDto {

    private Password oldPassword;

    private Password newPassword;
}
