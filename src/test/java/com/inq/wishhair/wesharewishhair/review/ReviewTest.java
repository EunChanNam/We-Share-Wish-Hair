package com.inq.wishhair.wesharewishhair.review;

import com.inq.wishhair.wesharewishhair.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.fixture.ReviewFixture;
import com.inq.wishhair.wesharewishhair.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.enums.Score;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Review 도메인 테스트")
public class ReviewTest {

    @Test
    @DisplayName("리뷰를 생성한다")
    void test1() {
        //given
        User user = UserFixture.B.toEntity();
        HairStyle hairStyle = HairStyleFixture.A.toEntity();

        //when
        Review result = Review.createReview(user, "hello", Score.S2H, new ArrayList<>(), hairStyle);

        //then
        assertAll(
                () -> assertThat(result.getHairStyle()).isEqualTo(hairStyle),
                () -> assertThat(result.getLikes()).isZero(),
                () -> assertThat(result.getUser()).isEqualTo(user),
                () -> assertThat(result.getPhotos()).isEmpty(),
                () -> assertThat(result.getScore()).isEqualTo(Score.S2H),
                () -> assertThat(result.getContents()).isEqualTo("hello")
        );
    }
}
