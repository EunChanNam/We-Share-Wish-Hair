package com.inq.wishhair.wesharewishhair.hairstyle.domain;

import com.inq.wishhair.wesharewishhair.domain.hairstyle.HairStyle;
import com.inq.wishhair.wesharewishhair.domain.hashtag.HashTag;
import com.inq.wishhair.wesharewishhair.domain.photo.entity.Photo;
import com.inq.wishhair.wesharewishhair.fixture.HairStyleFixture;
import org.junit.jupiter.api.Assertions;
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
        HairStyleFixture A = HairStyleFixture.MAN;

        //when -> toEntity() 안에 생성 메서드 포함
        HairStyle hairStyle = A.toEntity();

        //then
        assertAll(
                () -> assertThat(hairStyle.getName()).isEqualTo(A.getName()),
                () -> assertThat(hairStyle.getSex()).isEqualTo(A.getSex()),
                () -> assertThat(hairStyle.getPhotos().stream()
                        .map(Photo::getOriginalFilename).toList())
                        .containsAll(A.getOriginalFilenames()),
                () -> assertThat(hairStyle.getHashTags().stream()
                        .map(HashTag::getTag).toList())
                        .containsAll(A.getTags())
        );

    }
}
