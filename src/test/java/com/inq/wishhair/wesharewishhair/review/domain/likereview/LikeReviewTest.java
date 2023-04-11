package com.inq.wishhair.wesharewishhair.review.domain.likereview;

import com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.global.fixture.ReviewFixture;
import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;

@DisplayName("Review-LikeReview 도메인 테스트")
public class LikeReviewTest {

    @Test
    @DisplayName("좋아요를 생성한다")
    void test1() {
        //given
        User user = UserFixture.B.toEntity();
        HairStyle hairStyle = HairStyleFixture.A.toEntity();
        Review review = ReviewFixture.A.toEntity(user, hairStyle);

        //when
        LikeReview likeReview = LikeReview.createLikeReview(user, review);

        //then
        assertAll(
                () -> assertThat(likeReview.getReview()).isEqualTo(review),
                () -> assertThat(likeReview.getUser()).isEqualTo(user)
        );
    }

    @Nested
    @DisplayName("현재 좋아요의 유저와 입력된 유저가 같은지 확인한다")
    class isSameUser {

        private LikeReview likeReview;

        @BeforeEach
        void setUp() {
            //given
            User user = Mockito.mock(User.class);
            HairStyle hairStyle = HairStyleFixture.A.toEntity();
            Review review = ReviewFixture.A.toEntity(user, hairStyle);
            likeReview = LikeReview.createLikeReview(user, review);

            given(user.getId()).willReturn(1L);
        }

        @Test
        @DisplayName("같은 유저를 입력받아 true 를 반환한다")
        void test2() {
            //when
            boolean result = likeReview.isSameUser(1L);

            //then
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("다른 유저를 입력받아 false 를 반환한다")
        void test3() {
            //when
            boolean result = likeReview.isSameUser(2L);

            //then
            assertThat(result).isFalse();
        }
    }
}
