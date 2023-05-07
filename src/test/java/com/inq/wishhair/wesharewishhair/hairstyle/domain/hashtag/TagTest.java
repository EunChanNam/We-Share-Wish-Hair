package com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.enums.Tag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tag - 도매인 테스트")
public class TagTest {

    @Nested
    @DisplayName("태그가 얼굴형 태그인지 확인한다")
    class isFaceShapeType {
        @Test
        @DisplayName("얼굴형 태그로 true")
        void isTrue() {
            //given
            Tag tag = Tag.OBLONG;

            //when
            boolean result = tag.isFaceShapeType();

            //then
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("얼굴형 태그가 아니므로 false")
        void isFalse() {
            //given
            Tag tag = Tag.BANGS;

            //when
            boolean result = tag.isFaceShapeType();

            //then
            assertThat(result).isFalse();
        }
    }
}
