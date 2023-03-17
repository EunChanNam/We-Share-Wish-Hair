package com.inq.wishhair.wesharewishhair.user.domain;

import com.inq.wishhair.wesharewishhair.user.User;
import com.inq.wishhair.wesharewishhair.fixture.UserFixture;
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
                .loginId(A.getLoginId())
                .pw(A.getPw())
                .name(A.getName())
                .nickname(A.getNickname())
                .sex(A.getSex())
                .build();

        assertAll(
                () -> assertThat(user.getLoginId()).isEqualTo(A.getLoginId()),
                () -> assertThat(user.getPw()).isEqualTo(A.getPw()),
                () -> assertThat(user.getName()).isEqualTo(A.getName()),
                () -> assertThat(user.getNickname()).isEqualTo(A.getNickname()),
                () -> assertThat(user.getSex()).isEqualTo(A.getSex())
        );
    }
}
