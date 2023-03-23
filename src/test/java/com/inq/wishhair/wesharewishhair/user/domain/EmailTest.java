package com.inq.wishhair.wesharewishhair.user.domain;

import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

public class EmailTest {
    private static final String EMAIL = "namhm12@naver.com";
    private static final String WRONG_EMAIL1 = "namhm12naver.com";
    private static final String WRONG_EMAIL2 = "namhm12@navercom";

    @Test
    @DisplayName("올바른 이메일 형식으로 생성에 성공한다")
    void test1() {
        //when
        Email result = new Email(EMAIL);

        //then
        assertThat(result.getValue()).isEqualTo(EMAIL);
    }

    @Test
    @DisplayName("올바르지 않은 이메일 형식으로 생성에 실패한다")
    void test2() {
        //given
        ErrorCode expectedError = ErrorCode.USER_INVALID_EMAIL;

        //when, then
        assertAll(
                () -> assertThatThrownBy(() -> new Email(WRONG_EMAIL1))
                        .isInstanceOf(WishHairException.class)
                        .hasMessageContaining(expectedError.getMessage()),
                () -> assertThatThrownBy(() -> new Email(WRONG_EMAIL2))
                        .isInstanceOf(WishHairException.class)
                        .hasMessageContaining(expectedError.getMessage())
        );
    }
}
