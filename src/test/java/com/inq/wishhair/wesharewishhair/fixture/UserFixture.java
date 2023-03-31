package com.inq.wishhair.wesharewishhair.fixture;

import com.inq.wishhair.wesharewishhair.user.domain.Email;
import com.inq.wishhair.wesharewishhair.user.domain.Nickname;
import com.inq.wishhair.wesharewishhair.user.domain.Password;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserFixture {

    A("namhm23@naver.com", "asdf1234@", "userA", "userA", Sex.MAN),
    B("namhm2@naver.com", "asdf1234@", "userB", "userB", Sex.WOMAN),
    C("namhm3@naver.com", "asdf1234@", "userC", "userC", Sex.MAN);

    private final String email;

    private final String password;

    private final String name;

    private final String nickname;

    private final Sex sex;

    public User toEntity() {
        return User.builder()
                .email(new Email(email))
                .password(new Password(password))
                .name(getName())
                .nickname(new Nickname(nickname))
                .sex(getSex())
                .build();
    }
}
