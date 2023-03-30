package com.inq.wishhair.wesharewishhair.user.domain;

import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("비밀번호 검증 테스트")
public class PasswordTest {

    private static final String CORRECT_PASSWORD = "asdf12341234@";
    private static final String WRONG_PASSWORD1 = "asdfasdf";
    private static final String WRONG_PASSWORD2 = "123432143";
    private static final String WRONG_PASSWORD3 = "asdf123412";
    private static final String WRONG_PASSWORD4 = "a2@";
    private static final String WRONG_PASSWORD5 = "asdf";

    @Test
    @DisplayName("잘못된 비밀번호 형식으로 생성에 실패한다")
    void test1() {
        //given
        ErrorCode expectedError = ErrorCode.USER_INVALID_PASSWORD;

        //when, then
        assertAll(
                () -> assertThatThrownBy(() -> new Password(WRONG_PASSWORD1))
                        .isInstanceOf(WishHairException.class)
                        .hasMessageContaining(expectedError.getMessage()),
                () -> assertThatThrownBy(() -> new Password(WRONG_PASSWORD2))
                        .isInstanceOf(WishHairException.class)
                        .hasMessageContaining(expectedError.getMessage()),
                () -> assertThatThrownBy(() -> new Password(WRONG_PASSWORD3))
                        .isInstanceOf(WishHairException.class)
                        .hasMessageContaining(expectedError.getMessage()),
                () -> assertThatThrownBy(() -> new Password(WRONG_PASSWORD4))
                        .isInstanceOf(WishHairException.class)
                        .hasMessageContaining(expectedError.getMessage()),
                () -> assertThatThrownBy(() -> new Password(WRONG_PASSWORD5))
                        .isInstanceOf(WishHairException.class)
                        .hasMessageContaining(expectedError.getMessage())
        );
    }

    @Test
    @DisplayName("올바른 비밀번호 형식으로 생성에 성공한다")
    void test2() {
        //when
        Password result = new Password(CORRECT_PASSWORD);

        //then
        assertThat(result.getValue()).isEqualTo(CORRECT_PASSWORD);
    }
}
