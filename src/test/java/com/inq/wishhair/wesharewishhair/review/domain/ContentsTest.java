package com.inq.wishhair.wesharewishhair.review.domain;

import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Review-Contents 도매인 테스트")
public class ContentsTest {

    @Nested
    @DisplayName("Contents 생성 테스트")
    class construct {

        @Test
        @DisplayName("올바르지 않은 길이의 Contents 로 생성에 실패한다")
        void failByLength() {
            //when, then - 짧은 길이
            assertThatThrownBy(() -> new Contents("얍"))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(ErrorCode.CONTENTS_INVALID_LENGTH.getMessage());

            //when, then - 긴 길이
            assertThatThrownBy(() -> new Contents("얍".repeat(101)))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(ErrorCode.CONTENTS_INVALID_LENGTH.getMessage());
        }

        @Test
        @DisplayName("Contents 생성에 성공한다")
        void success() {
            //when
            Contents contents = new Contents("Hello");

            //then
            assertThat(contents.getValue()).isEqualTo("Hello");
        }
    }
}
