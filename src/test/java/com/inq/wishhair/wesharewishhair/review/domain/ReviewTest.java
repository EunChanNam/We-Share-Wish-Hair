package com.inq.wishhair.wesharewishhair.review.domain;

import com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.global.fixture.ReviewFixture;
import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.review.domain.enums.Score;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;

@DisplayName("Review 도메인 테스트")
public class ReviewTest {

    private final User user = UserFixture.B.toEntity();
    private final HairStyle hairStyle = HairStyleFixture.A.toEntity();

    @Test
    @DisplayName("리뷰를 생성한다")
    void test1() {
        //when
        Review result = Review.createReview(user, "hello", Score.S2H, new ArrayList<>(), hairStyle);

        //then
        assertAll(
                () -> assertThat(result.getHairStyle()).isEqualTo(hairStyle),
                () -> assertThat(result.getLikes()).isZero(),
                () -> assertThat(result.getUser()).isEqualTo(user),
                () -> assertThat(result.getPhotos()).isEmpty(),
                () -> assertThat(result.getScore()).isEqualTo(Score.S2H),
                () -> assertThat(result.getContentsValue()).isEqualTo("hello")
        );
    }

    @Test
    @DisplayName("좋아요 수를 조회한다")
    void test2() {
        //given
        Review review = ReviewFixture.A.toEntity(user, hairStyle);

        //when
        long likes = review.getLikes();

        //then
        assertThat(likes).isZero();
    }

    @Nested
    @DisplayName("좋아요를 실행한다")
    class executeLike {

        private Review review;

        @BeforeEach
        void setUp() {
            //given
            review = Review.createReview(user, "hello", Score.S2H, new ArrayList<>(), hairStyle);
        }

        @Test
        @DisplayName("좋아요를 실행한 유저가 좋아요를 하지 않은 유저여서 새로운 좋아요가 생긴다")
        void test3() {
            //given
            User mockUser = Mockito.mock(User.class);

            //when
            review.executeLike(mockUser);

            //then
            assertAll(
                    () -> assertThat(review.getLikes()).isEqualTo(1),
                    () -> assertThat(review.getLikeReviews()).hasSize(1)
            );
        }

        @Test
        @DisplayName("좋아요를 실행한 유저가 이미 좋아요를 한 유저여서 기존 좋아요가 취소된다")
        void test4() {
            //given
            User mockUser = Mockito.mock(User.class);
            given(mockUser.getId()).willReturn(1L);

            //when
            review.executeLike(mockUser);
            review.executeLike(mockUser);

            //then
            assertAll(
                    () -> assertThat(review.getLikes()).isZero(),
                    () -> assertThat(review.getLikeReviews()).isEmpty()
            );
        }
    }
}
