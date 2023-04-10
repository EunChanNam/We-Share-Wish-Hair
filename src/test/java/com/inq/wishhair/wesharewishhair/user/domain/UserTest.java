package com.inq.wishhair.wesharewishhair.user.domain;

import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("User Domain Test")
public class UserTest {

    @Test
    @DisplayName("User 생성 메서드 테스트")
    void createMemberTest() {
        UserFixture A = UserFixture.A;

        User user = User.builder()
                .email(new Email(A.getEmail()))
                .password(new Password(A.getPassword()))
                .name(A.getName())
                .nickname(new Nickname(A.getNickname()))
                .sex(A.getSex())
                .build();

        assertAll(
                () -> assertThat(user.getEmailValue()).isEqualTo(A.getEmail()),
                () -> assertThat(user.getPasswordValue()).isEqualTo(A.getPassword()),
                () -> assertThat(user.getName()).isEqualTo(A.getName()),
                () -> assertThat(user.getNicknameValue()).isEqualTo(A.getNickname()),
                () -> assertThat(user.getSex()).isEqualTo(A.getSex())
        );
    }
}
