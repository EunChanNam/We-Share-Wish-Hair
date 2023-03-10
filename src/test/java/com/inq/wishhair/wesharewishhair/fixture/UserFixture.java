package com.inq.wishhair.wesharewishhair.fixture;

import com.inq.wishhair.wesharewishhair.domain.user.User;
import com.inq.wishhair.wesharewishhair.domain.user.enums.Sex;
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

    public User toEntity(UserFixture fixture) {
        return User.builder()
                .loginId(fixture.loginId)
                .pw(fixture.getPw())
                .name(fixture.getName())
                .nickname(fixture.getNickname())
                .sex(fixture.getSex())
                .build();
    }
}
