package com.inq.wishhair.wesharewishhair.user.controller.utils;

import com.inq.wishhair.wesharewishhair.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.UserCreateRequest;

public abstract class UserCreateRequestUtils {
    private static final UserFixture a = UserFixture.A;

    public static UserCreateRequest createRequest() {
        return new UserCreateRequest(
                a.getLoginId(),
                a.getPw(),
                a.getName(),
                a.getNickname(),
                a.getSex()
        );
    }
}
