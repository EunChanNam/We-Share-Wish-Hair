package com.inq.wishhair.wesharewishhair.domain.login.dto;

import com.inq.wishhair.wesharewishhair.domain.user.User;
import com.inq.wishhair.wesharewishhair.domain.user.enums.Sex;
import lombok.Getter;

@Getter
public class UserSessionDto {

    private final Long id;

    private final String name;

    private final String nickname;

    private final String loginId;

    private final Sex sex;

    public UserSessionDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.loginId = user.getLoginId();
        this.sex = user.getSex();
    }
}
