package com.inq.wishhair.wesharewishhair.user.service;

import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.PasswordUpdateRequest;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.UserUpdateRequest;
import com.inq.wishhair.wesharewishhair.user.controller.utils.UserUpdateRequestUtils;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.inq.wishhair.wesharewishhair.global.fixture.UserFixture.*;
import static com.inq.wishhair.wesharewishhair.user.controller.utils.PasswordUpdateRequestUtils.*;
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

    @Nested
    @DisplayName("회원 탈퇴 서비스")
    class deleteUser {
        @Test
        @DisplayName("회원 탈퇴에 성공한다")
        void success() {
            //when
            User user = userRepository.save(B.toEntity());
            userService.deleteUser(user.getId());

            //then
            List<User> result = userRepository.findAll();
            assertThat(result).isEmpty();
        }
    }

    @Nested
    @DisplayName("회원 정보 수정 서비스")
    class updateUser {

        private User user;

        @BeforeEach
        void setUp() {
            //given
            user = userRepository.save(B.toEntity());
        }

        @Test
        @DisplayName("회원의 닉네임과 성별을 수정에 성공한다")
        void success() {
            //given
            UserUpdateRequest request = UserUpdateRequestUtils.request(A);

            //when
            userService.updateUser(user.getId(), request);

            //then
            assertAll(
                    () -> assertThat(user.getNicknameValue()).isEqualTo(request.getNickname()),
                    () -> assertThat(user.getSex()).isEqualTo(request.getSex())
            );

        }

        @Test
        @DisplayName("중복된 닉네임으로 수정에 실패한다")
        void failByDuplicatedNickname() {
            //given
            UserUpdateRequest request = UserUpdateRequestUtils.request(B);

            //when, then
            assertThatThrownBy(() -> userService.updateUser(user.getId(), request))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(ErrorCode.USER_DUPLICATED_NICKNAME.getMessage());
        }
    }

    @Nested
    @DisplayName("비밀번호 변경 서비스")
    class updatePassword {

        private User user;

        @BeforeEach
        void setUp() {
            //given
            user = userRepository.save(A.toEntity());
        }

        @Test
        @DisplayName("현재 비밀번호가 틀려 실패한다")
        void failByOldPassword() {
            //given
            PasswordUpdateRequest request = wrongOldPwRequest();

            //when, then
            assertThatThrownBy(() -> userService.updatePassword(user.getId(), request))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(ErrorCode.USER_WRONG_PASSWORD.getMessage());
        }

        @Test
        @DisplayName("비밀번호 변경에 성공한다")
        void success() {
            //given
            PasswordUpdateRequest request = request(A);

            //when
            userService.updatePassword(user.getId(), request);

            //then
            User result = userRepository.findById(user.getId()).get();
            assertThat(result.getPasswordValue()).isEqualTo(request.getNewPassword());
        }
    }
}