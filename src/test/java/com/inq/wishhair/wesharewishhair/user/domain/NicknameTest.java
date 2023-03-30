package com.inq.wishhair.wesharewishhair.user.domain;

import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("닉네임 검증 테스트")
public class NicknameTest {

    private static final String CORRECT_NICKNAME = "잘생긴사람";
    private static final String WRONG_NICKNAME1 = "니";
    private static final String WRONG_NICKNAME2 = "니니니니니니니니니니니니니니니";
    private static final String WRONG_NICKNAME3 = "니니       니니";

    @Test
    @DisplayName("올바르지 않은 닉네임으로 생성에 실패한다")
    void test1() {
        //given
        ErrorCode expectedError = ErrorCode.USER_INVALID_NICKNAME;

        //when, then
        assertAll(
                () -> assertThatThrownBy(() -> new Nickname(WRONG_NICKNAME1))
                        .isInstanceOf(WishHairException.class)
                        .hasMessageContaining(expectedError.getMessage()),
                () -> assertThatThrownBy(() -> new Nickname(WRONG_NICKNAME2))
                        .isInstanceOf(WishHairException.class)
                        .hasMessageContaining(expectedError.getMessage()),
                () -> assertThatThrownBy(() -> new Nickname(WRONG_NICKNAME3))
                        .isInstanceOf(WishHairException.class)
                        .hasMessageContaining(expectedError.getMessage())
        );
    }

    @Test
    @DisplayName("올바른 닉네임으로 생성에 성공한다")
    void test2() {
        //when
        Nickname result = new Nickname(CORRECT_NICKNAME);

        //then
        assertThat(result.getValue()).isEqualTo(CORRECT_NICKNAME);
    }
}
