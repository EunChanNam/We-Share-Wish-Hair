package com.inq.wishhair.wesharewishhair.common.utils;

import com.inq.wishhair.wesharewishhair.domain.login.dto.UserSessionDto;
import com.inq.wishhair.wesharewishhair.domain.user.User;
import com.inq.wishhair.wesharewishhair.fixture.UserFixture;

public abstract class UserSessionDtoUtils {

    public static UserSessionDto getASessionDto() {
        return new UserSessionDto(createUser(UserFixture.A));
    }

    public static UserSessionDto getBSessionDto() {
        return new UserSessionDto(createUser(UserFixture.B));
    }

    private static User createUser(UserFixture fixture) {
        return fixture.toEntity();
    }
}
