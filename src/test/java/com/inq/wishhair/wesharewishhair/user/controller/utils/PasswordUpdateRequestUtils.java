package com.inq.wishhair.wesharewishhair.user.controller.utils;

import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.PasswordUpdateRequest;

public abstract class PasswordUpdateRequestUtils {

    private static final String NEW_PW = "gogo3214@";

    public static PasswordUpdateRequest request(UserFixture fixture) {
        return new PasswordUpdateRequest(fixture.getPassword(), NEW_PW);
    }

    public static PasswordUpdateRequest wrongOldPwRequest() {
        return new PasswordUpdateRequest("gogo9999!", NEW_PW);
    }
}
