package com.inq.wishhair.wesharewishhair.user.domain;

import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.inq.wishhair.wesharewishhair.global.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("User Domain Test")
public class UserTest {

    @Test
    @DisplayName("User 생성 메서드 테스트")
    void createMemberTest() {
        //when
        User user = A.toEntity();

        //then
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        assertAll(
                () -> assertThat(user.getEmailValue()).isEqualTo(A.getEmail()),
                () -> assertThat(passwordEncoder.matches(A.getPassword(), user.getPasswordValue())).isTrue(),
                () -> assertThat(user.getName()).isEqualTo(A.getName()),
                () -> assertThat(user.getNicknameValue()).isEqualTo(A.getNickname()),
                () -> assertThat(user.getSex()).isEqualTo(A.getSex())
        );
    }
}
