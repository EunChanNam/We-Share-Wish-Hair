package com.inq.wishhair.wesharewishhair.review.likereview;

import com.inq.wishhair.wesharewishhair.fixture.ReviewFixture;
import com.inq.wishhair.wesharewishhair.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.domain.likereview.LikeReview;
import com.inq.wishhair.wesharewishhair.review.domain.likereview.LikeReviews;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;

@DisplayName("Review-LikeReviews 도메인 테스트")
public class LikeReviewsTest {

    private Review review;

    @BeforeEach
    void setUp() {
        //given
        review = ReviewFixture.A.toEntity(UserFixture.A.toEntity(), null);
    }

    @Nested
    @DisplayName("좋아요를 실행한다")
    class executeLike {
        @Test
        @DisplayName("좋아요를 실행한 유저가 좋아요를 하지 않은 유저여서 새로운 좋아요가 생긴다")
        void test1() {
            //given
            LikeReviews likeReviews = new LikeReviews();
            User mockUser = Mockito.mock(User.class);

            //when
            likeReviews.executeLike(mockUser, review);

            //then
            List<LikeReview> likes = likeReviews.getLikeReviews();
            assertAll(
                    () -> assertThat(likes).hasSize(1),
                    () -> assertThat(likes.get(0).getReview()).isEqualTo(review),
                    () -> assertThat(likes.get(0).getUser()).isEqualTo(mockUser),
                    () -> assertThat(likeReviews.getLikes()).isEqualTo(1)
            );
        }

        @Test
        @DisplayName("좋아요를 실행한 유저가 이미 좋아요를 한 유저여서 기존 좋아요가 취소된다")
        void test2() {
            //given
            LikeReviews likeReviews = new LikeReviews();
            User mockUser = Mockito.mock(User.class);
            given(mockUser.getId()).willReturn(1L);

            //when
            likeReviews.executeLike(mockUser, review);
            likeReviews.executeLike(mockUser, review);

            //then
            assertAll(
                    () -> assertThat(likeReviews.getLikeReviews()).isEmpty(),
                    () -> assertThat(likeReviews.getLikes()).isZero()
            );
        }
    }
}
