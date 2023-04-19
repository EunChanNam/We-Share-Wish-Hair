package com.inq.wishhair.wesharewishhair.user.service;

import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.enums.Tag;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.FaceShapeUpdateRequest;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.PasswordUpdateRequest;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.SignUpRequest;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.UserUpdateRequest;
import com.inq.wishhair.wesharewishhair.user.controller.utils.SignUpRequestUtils;
import com.inq.wishhair.wesharewishhair.user.controller.utils.UserUpdateRequestUtils;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static com.inq.wishhair.wesharewishhair.global.fixture.UserFixture.*;
import static com.inq.wishhair.wesharewishhair.user.controller.utils.PasswordUpdateRequestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("UserServiceTest - SpringBootTest")
class UserServiceTest extends ServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Nested
    @DisplayName("회원가입 서비스 테스트")
    class createUser {
        @Test
        @DisplayName("회원가입에 성공한다")
        void success() {
            //given
            SignUpRequest request = SignUpRequestUtils.successRequest();

            //when
            Long result = userService.createUser(request);

            //then
            Optional<User> actual = userRepository.findById(result);
            assertAll(
                    () -> assertThat(actual).isPresent(),
                    () -> {
                        User user = actual.orElseThrow();
                        assertThat(user.getName()).isEqualTo(request.getName());
                        assertThat(user.getAvailablePoint()).isZero();
                        assertThat(user.getSex()).isEqualTo(request.getSex());
                        assertThat(user.getEmailValue()).isEqualTo(request.getEmail());
                        assertThat(passwordEncoder.matches(request.getPw(), user.getPasswordValue())).isTrue();
                        assertThat(user.getNicknameValue()).isEqualTo(request.getNickname());
                    }
            );
        }

        @Test
        @DisplayName("중복된 닉네임으로 실패한다")
        void failByDuplicatedNickname() {
            //given
            SignUpRequest request = SignUpRequestUtils.successRequest();
            userRepository.save(A.toEntity());

            //when, then
            assertThatThrownBy(() -> userService.createUser(request))
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
            assertThat(passwordEncoder.matches(request.getNewPassword(), user.getPasswordValue())).isTrue();
        }
    }

    @Test
    @DisplayName("사용자 얼굴형 업데이트 서비스")
    void updateFaceShape() {
        //given
        User user = userRepository.save(A.toEntity());
        FaceShapeUpdateRequest request = new FaceShapeUpdateRequest(Tag.OBLONG);

        //when
        userService.updateFaceShape(user.getId(), request.getFaceShapeTag());

        //then
        assertAll(
                () -> assertThat(user.existFaceShape()).isTrue(),
                () -> assertThat(user.getFaceShape()).isEqualTo(request.getFaceShapeTag())
        );
    }
}