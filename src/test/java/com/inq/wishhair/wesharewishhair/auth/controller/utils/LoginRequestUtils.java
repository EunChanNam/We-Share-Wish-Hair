package com.inq.wishhair.wesharewishhair.auth.controller.utils;

import com.inq.wishhair.wesharewishhair.auth.controller.dto.request.LoginRequest;

public abstract class LoginRequestUtils {

    private static final String loginId = "loginId";
    private static final String pw = "pw";

    public static LoginRequest createRequest() {
        return new LoginRequest(loginId, pw);
    }
}
