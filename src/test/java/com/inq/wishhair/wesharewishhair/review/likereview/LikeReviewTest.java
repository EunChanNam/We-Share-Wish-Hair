package com.inq.wishhair.wesharewishhair.review.likereview;

import com.inq.wishhair.wesharewishhair.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.fixture.ReviewFixture;
import com.inq.wishhair.wesharewishhair.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.domain.likereview.LikeReview;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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
}
