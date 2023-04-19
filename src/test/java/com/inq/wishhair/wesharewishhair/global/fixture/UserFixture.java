package com.inq.wishhair.wesharewishhair.global.fixture;

import com.inq.wishhair.wesharewishhair.user.domain.Email;
import com.inq.wishhair.wesharewishhair.user.domain.Nickname;
import com.inq.wishhair.wesharewishhair.user.domain.Password;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return User.createUser(
                email,
                Password.encrypt(password, passwordEncoder),
                name,
                nickname,
                sex
        );
    }
}
