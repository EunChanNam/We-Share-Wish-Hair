package com.inq.wishhair.wesharewishhair.user.service;

import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.user.domain.Email;
import com.inq.wishhair.wesharewishhair.user.domain.Nickname;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("UserValidatorTest - SpringBootTest")
public class UserValidatorTest extends ServiceTest {

    @Autowired
    private UserValidator userValidator;

    private User user;

    @BeforeEach
    void sstUp() {
        //given
        user = userRepository.save(UserFixture.A.toEntity());
    }

    @Test
    @DisplayName("닉네임 중복검증을 한다")
    void validateNicknameIsNotDuplicated() {
        //when, then
        assertThatThrownBy(() -> userValidator.validateNicknameIsNotDuplicated(user.getNickname()))
                .isInstanceOf(WishHairException.class)
                .hasMessageContaining(ErrorCode.USER_DUPLICATED_NICKNAME.getMessage());

        Nickname ableNickname = new Nickname("nickname");
        assertDoesNotThrow(() -> userValidator.validateNicknameIsNotDuplicated(ableNickname));
    }

    @Test
    @DisplayName("이메일 중복검증을 한다")
    void validateEmailIsNotDuplicated() {
        //when, then
        assertThatThrownBy(() -> userValidator.validateEmailIsNotDuplicated(user.getEmail()))
                .isInstanceOf(WishHairException.class)
                .hasMessageContaining(ErrorCode.USER_DUPLICATED_EMAIL.getMessage());

        Email ableEmail = new Email("email@naver.com");
        assertDoesNotThrow(() -> userValidator.validateEmailIsNotDuplicated(ableEmail));
    }
}
