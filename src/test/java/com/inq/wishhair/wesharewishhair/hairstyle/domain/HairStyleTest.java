package com.inq.wishhair.wesharewishhair.hairstyle.domain;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.HashTag;
import com.inq.wishhair.wesharewishhair.photo.domain.Photo;
import com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("HairStyle Domain Test")
public class HairStyleTest {

    @Test
    @DisplayName("HairStyle 생성 메서드 테스트")
    void createHairStyleTest() {
        //given
        HairStyleFixture B = HairStyleFixture.B;

        //when -> toEntity() 안에 생성 메서드 포함
        HairStyle hairStyle = B.toEntity();

        //then
        assertAll(
                () -> assertThat(hairStyle.getName()).isEqualTo(B.getName()),
                () -> assertThat(hairStyle.getSex()).isEqualTo(B.getSex()),
                () -> assertThat(hairStyle.getPhotos().stream()
                        .map(Photo::getStoreUrl).toList())
                        .containsAll(B.getFilenames()),
                () -> assertThat(hairStyle.getHashTags().stream()
                        .map(HashTag::getTag).toList())
                        .containsAll(B.getTags())
        );
    }
}
