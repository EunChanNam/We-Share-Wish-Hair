package com.inq.wishhair.wesharewishhair.review.domain;

import com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.global.fixture.ReviewFixture;
import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.photo.domain.Photo;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import org.junit.jupiter.api.*;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Review 도메인 테스트")
public class ReviewTest {

    private final User user = UserFixture.B.toEntity();
    private final HairStyle hairStyle = HairStyleFixture.A.toEntity();

    @Test
    @DisplayName("리뷰를 생성한다")
    void createReview() {
        //when
        Review result = Review.createReview(user, "hello", Score.S2H, new ArrayList<>(), hairStyle);

        //then
        assertAll(
                () -> assertThat(result.getHairStyle()).isEqualTo(hairStyle),
                () -> assertThat(result.getWriter()).isEqualTo(user),
                () -> assertThat(result.getPhotos()).isEmpty(),
                () -> assertThat(result.getScore()).isEqualTo(Score.S2H),
                () -> assertThat(result.getContentsValue()).isEqualTo("hello")
        );
    }

    @Nested
    @DisplayName("리뷰의 작성자인지 확인한다")
    class isWriter {

        private final Review review = ReviewFixture.A.toEntity(user, null);

        @Test
        @DisplayName("작성자로 true 를 리턴한다")
        void isTrue() {
            //given
            ReflectionTestUtils.setField(user, "id", 1L);

            //when
            boolean result = review.isWriter(1L);

            //then
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("작성자가 아니여서 false 를 리턴한다")
        void isFalse() {
            //given
            ReflectionTestUtils.setField(user, "id", 1L);

            //when
            boolean result = review.isWriter(2L);

            //then
            assertThat(result).isFalse();
        }
    }

    @Test
    @DisplayName("리뷰를 업데이트한다")
    void updateReview() {
        //given
        Review review = ReviewFixture.A.toEntity(null, null);

        final Contents newContents = new Contents("new Contents");
        final Score newScore = Score.S1H;
        final List<String> newStoreUrls = List.of("new1.png", "new2.png");

        //when
        review.updateReview(newContents, newScore, newStoreUrls);

        //then
        assertAll(
                () -> assertThat(review.getContents()).isEqualTo(newContents),
                () -> assertThat(review.getScore()).isEqualTo(newScore),
                () -> {
                    List<String> actual = review.getPhotos().stream().map(Photo::getStoreUrl).toList();
                    assertThat(actual).containsAll(newStoreUrls);
                }
        );
    }
}
