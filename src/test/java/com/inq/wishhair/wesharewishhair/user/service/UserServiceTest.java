package com.inq.wishhair.wesharewishhair.user.service;

import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.user.controller.utils.UserCreateRequestUtils;
import com.inq.wishhair.wesharewishhair.user.domain.point.PointHistory;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.fixture.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.inq.wishhair.wesharewishhair.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("UserServiceTest - SpringBootTest")
class UserServiceTest extends ServiceTest {

    @Autowired
    private UserService userService;

    @Nested
    @DisplayName("회원가입 서비스 테스트")
    class createUser {
        @Test
        @DisplayName("회원가입에 성공한다")
        void success() {
            //given
            User user = A.toEntity();

            //when
            Long result = userService.createUser(user);

            assertThat(result).isEqualTo(user.getId());
        }

        @Test
        @DisplayName("중복된 닉네임으로 실패한다")
        void failByDuplicatedNickname() {
            //given
            userRepository.save(A.toEntity());

            //when, then
            assertThatThrownBy(() -> userService.createUser(A.toEntity()))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(ErrorCode.USER_DUPLICATED_NICKNAME.getMessage());
        }
    }
}