package com.inq.wishhair.wesharewishhair.login.controller.utils;


import com.inq.wishhair.wesharewishhair.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.web.login.dto.LoginRequest;

public class LoginRequestUtils {

    private static final UserFixture A = UserFixture.A;
    private static final String wrongLoginId = A.getLoginId() + "fail";

    public static LoginRequest createRequest() {
        return new LoginRequest(
                A.getLoginId(),
                A.getPw()
        );
    }

    public static LoginRequest createWrongLoginIdRequest() {
        return new LoginRequest(
                wrongLoginId,
                A.getPw()
        );
    }
}
