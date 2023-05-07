package com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.enums.TagType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("TagType - 도매인 테스트")
public class TagTypeTest {

    @Nested
    @DisplayName("얼굴형 타입인지 확인한다")
    class isFaceType {
        @Test
        @DisplayName("얼굴형 타입으로 true")
        void isTrue() {
            //given
            TagType tagType = TagType.FACE_SHAPE;

            //when
            boolean result = tagType.isFaceType();

            //then
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("얼굴형 타입이 아니므로 false")
        void isFalse() {
            //given
            TagType tagType = TagType.NORMAL;

            //when
            boolean result = tagType.isFaceType();

            //then
            assertThat(result).isFalse();
        }
    }
}
