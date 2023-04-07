package com.inq.wishhair.wesharewishhair.user.controller.dto.request;

import com.inq.wishhair.wesharewishhair.user.domain.Nickname;
import com.inq.wishhair.wesharewishhair.user.enums.Sex;
import com.inq.wishhair.wesharewishhair.user.service.dto.UserUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserUpdateRequest {

    private String nickname;

    private Sex sex;
}
