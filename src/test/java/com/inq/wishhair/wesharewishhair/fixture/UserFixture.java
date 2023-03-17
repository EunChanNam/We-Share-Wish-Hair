package com.inq.wishhair.wesharewishhair.fixture;

import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserFixture {

    A("userA", "testA", "userA", "userA", Sex.MAN),
    B("userB", "testB", "userB", "userB", Sex.WOMAN),
    C("userC", "testC", "userC", "userC", Sex.MAN);

    private final String loginId;

    private final String pw;

    private final String name;

    private final String nickname;

    private final Sex sex;

    public User toEntity() {
        return User.builder()
                .loginId(loginId)
                .pw(getPw())
                .name(getName())
                .nickname(getNickname())
                .sex(getSex())
                .build();
    }
}
