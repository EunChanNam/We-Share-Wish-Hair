package com.inq.wishhair.wesharewishhair.auth.controller.utils;

import com.inq.wishhair.wesharewishhair.auth.controller.dto.request.LoginRequest;

public abstract class LoginRequestUtils {

    private static final String loginId = "hello12@naver.com";
    private static final String pw = "hello1234!";

    public static LoginRequest createRequest() {
        return new LoginRequest(loginId, pw);
    }
}
