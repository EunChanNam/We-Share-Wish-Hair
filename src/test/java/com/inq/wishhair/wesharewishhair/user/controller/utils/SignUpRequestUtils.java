package com.inq.wishhair.wesharewishhair.user.controller.utils;

import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.SignUpRequest;

public abstract class SignUpRequestUtils {
    private static final UserFixture a = UserFixture.A;

    public static SignUpRequest successRequest() {
        return new SignUpRequest(
                a.getEmail(),
                a.getPassword(),
                a.getName(),
                a.getNickname(),
                a.getSex()
        );
    }

    public static SignUpRequest wrongEmailRequest() {
        return new SignUpRequest(
                "wrongEmail121",
                a.getPassword(),
                a.getName(),
                a.getNickname(),
                a.getSex()
        );
    }

    public static SignUpRequest wrongPasswordRequest() {
        return new SignUpRequest(
                a.getEmail(),
                "12341234",
                a.getName(),
                a.getNickname(),
                a.getSex()
        );
    }

    public static SignUpRequest wrongNicknameRequest() {
        return new SignUpRequest(
                a.getEmail(),
                a.getPassword(),
                a.getName(),
                "닉",
                a.getSex()
        );
    }
}
