package com.inq.wishhair.wesharewishhair.user.service.dto;

import com.inq.wishhair.wesharewishhair.user.domain.Nickname;
import com.inq.wishhair.wesharewishhair.user.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserUpdateDto {

    private Nickname nickname;

    private Sex sex;
}
