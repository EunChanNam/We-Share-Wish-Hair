package com.inq.wishhair.wesharewishhair.common.utils;

import com.inq.wishhair.wesharewishhair.domain.login.dto.UserSessionDto;
import com.inq.wishhair.wesharewishhair.domain.user.User;
import com.inq.wishhair.wesharewishhair.fixture.UserFixture;

public abstract class UserSessionDtoUtils {

    public static UserSessionDto getSessionDto() {
        return new UserSessionDto(createUser());
    }

    private static User createUser() {
        return UserFixture.A.toEntity();
    }
}
