package com.inq.wishhair.wesharewishhair.user.controller.utils;

import com.inq.wishhair.wesharewishhair.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.UserCreateRequest;

public abstract class UserCreateRequestUtils {
    private static final UserFixture a = UserFixture.A;

    public static UserCreateRequest successRequest() {
        return new UserCreateRequest(
                a.getEmail(),
                a.getPassword(),
                a.getName(),
                a.getNickname(),
                a.getSex()
        );
    }

    public static UserCreateRequest WrongEmailRequest() {
        return new UserCreateRequest(
                "wrongEmail121",
                a.getPassword(),
                a.getName(),
                a.getNickname(),
                a.getSex()
        );
    }

    public static UserCreateRequest WrongPasswordRequest() {
        return new UserCreateRequest(
                a.getEmail(),
                "12341234",
                a.getName(),
                a.getNickname(),
                a.getSex()
        );
    }

    public static UserCreateRequest WrongNicknameRequest() {
        return new UserCreateRequest(
                a.getEmail(),
                a.getPassword(),
                a.getName(),
                "닉",
                a.getSex()
        );
    }
}
