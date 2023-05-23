package com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag;

import com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("HashTag - 도매인 테스트")
public class HashTagTest {

    @Test
    @DisplayName("HashTag 생성 메서드 테스트")
    void of() {
        //given
        HairStyle hairStyle = HairStyleFixture.A.toEntity();

        //when
        HashTag hashTag = HashTag.of(hairStyle, Tag.OBLONG);

        //then
        assertAll(
                () -> assertThat(hashTag.getTag()).isEqualTo(Tag.OBLONG),
                () -> assertThat(hashTag.getHairStyle()).isEqualTo(hairStyle)
        );
    }

    @Test
    @DisplayName("태그의 설명을 조회한다")
    void getDescription() {
        //given
        HashTag hashTag = HashTag.of(null, Tag.OBLONG);

        //when
        String description = hashTag.getDescription();

        //then
        assertThat(description).isEqualTo("직사각형");
    }
}
