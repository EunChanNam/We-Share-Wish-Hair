package com.inq.wishhair.wesharewishhair.user.service;

import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("UserFindServiceTest - SpringBootTest")
public class UserFindServiceTest extends ServiceTest {

    @Autowired
    private UserFindService userFindService;

    @Test
    @DisplayName("유저의 아이디로 유저를 조회한다.")
    void test1() {
        //given
        User user = UserFixture.A.toEntity();
        userRepository.save(user);

        //when
        User result = userFindService.findByUserId(user.getId());

        //then
        assertAll(
                () -> assertThat(result.getId()).isEqualTo(user.getId()),
                () -> assertThat(result.getName()).isEqualTo(user.getName()),
                () -> assertThat(result.getPassword()).isEqualTo(user.getPassword()),
                () -> assertThat(result.getEmail()).isEqualTo(user.getEmail())
        );

    }
}
