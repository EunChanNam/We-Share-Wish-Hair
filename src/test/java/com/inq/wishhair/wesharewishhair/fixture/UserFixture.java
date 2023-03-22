package com.inq.wishhair.wesharewishhair.fixture;

import com.inq.wishhair.wesharewishhair.user.domain.Email;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserFixture {

    A("namhm1@naver.com", "testA", "userA", "userA", Sex.MAN),
    B("namhm2@naver.com", "testB", "userB", "userB", Sex.WOMAN),
    C("namhm3@naver.com", "testC", "userC", "userC", Sex.MAN);

    private final String email;

    private final String pw;

    private final String name;

    private final String nickname;

    private final Sex sex;

    public User toEntity() {
        return User.builder()
                .email(new Email(email))
                .pw(getPw())
                .name(getName())
                .nickname(getNickname())
                .sex(getSex())
                .build();
    }
}
