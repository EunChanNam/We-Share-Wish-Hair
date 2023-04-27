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
        LikeReview likeReview = LikeReview.addLike(1L, 1L);

        //then
        assertAll(
                () -> assertThat(likeReview.getReviewId()).isEqualTo(1L),
                () -> assertThat(likeReview.getUserId()).isEqualTo(1L)
        );
    }
}
