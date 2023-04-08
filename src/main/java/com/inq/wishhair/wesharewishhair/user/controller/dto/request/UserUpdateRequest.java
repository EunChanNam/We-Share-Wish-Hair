package com.inq.wishhair.wesharewishhair.user.controller.dto.request;

import com.inq.wishhair.wesharewishhair.user.enums.Sex;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserUpdateRequest {

    private String nickname;

    private Sex sex;
}
