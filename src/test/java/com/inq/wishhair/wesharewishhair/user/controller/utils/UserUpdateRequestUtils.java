package com.inq.wishhair.wesharewishhair.user.controller.utils;

import com.inq.wishhair.wesharewishhair.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.UserUpdateRequest;

public abstract class UserUpdateRequestUtils {

    public static UserUpdateRequest request(UserFixture fixture) {
        return new UserUpdateRequest(fixture.getNickname(), fixture.getSex());
    }

    public static UserUpdateRequest wrongNicknameRequest(UserFixture fixture) {
        return new UserUpdateRequest("닉", fixture.getSex());
    }
}
