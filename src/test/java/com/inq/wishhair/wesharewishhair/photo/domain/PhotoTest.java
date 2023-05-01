package com.inq.wishhair.wesharewishhair.photo.domain;

import com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.global.fixture.ReviewFixture;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Photo 도메인 테스트")
public class PhotoTest {

    private final String STORE_URL = "hello.png";

    @Nested
    @DisplayName("Photo 생성 메서드 테스트")
    class createPhoto {
        @Test
        @DisplayName("리뷰 사진 생성")
        void createReviewPhoto() {
            //given
            Review review = ReviewFixture.A.toEntity(null, null);

            //when
            Photo result = Photo.createReviewPhoto(STORE_URL, review);

            //then
            assertAll(
                    () -> assertThat(result.getReview()).isEqualTo(review),
                    () -> assertThat(result.getStoreUrl()).isEqualTo(STORE_URL),
                    () -> assertThat(result.getHairStyle()).isNull()
            );
        }

        @Test
        @DisplayName("헤어스타일 사진 생성")
        void createHairStylePhoto() {
            //given
            HairStyle hairStyle = HairStyleFixture.A.toEntity();

            //when
            Photo result = Photo.createHairStylePhoto(STORE_URL, hairStyle);

            //then
            assertAll(
                    () -> assertThat(result.getHairStyle()).isEqualTo(hairStyle),
                    () -> assertThat(result.getStoreUrl()).isEqualTo(STORE_URL),
                    () -> assertThat(result.getReview()).isNull()
            );
        }
    }
}
