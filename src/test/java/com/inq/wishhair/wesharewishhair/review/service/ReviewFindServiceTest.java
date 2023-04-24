package com.inq.wishhair.wesharewishhair.review.service;

import com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.global.fixture.ReviewFixture;
import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("ReviewFindServiceTest - SpringBootTest")
public class ReviewFindServiceTest extends ServiceTest {

    @Autowired
    private ReviewFindService reviewFindService;

    private Review review;
    private User user;
    private HairStyle hairStyle;

    @BeforeEach
    void setUp() {
        user = userRepository.save(UserFixture.B.toEntity());
        hairStyle = hairStyleRepository.save(HairStyleFixture.A.toEntity());
        review = reviewRepository.save(ReviewFixture.A.toEntity(user, hairStyle));
    }

    @Test
    @DisplayName("아이디로 리뷰를 유저 정보와 함께 조회한다")
    void findWithUserById() {
        //when
        Review result = reviewFindService.findWithUserById(review.getId());

        //then
        assertThat(result).isEqualTo(review);
    }

    @Test
    @DisplayName("아이디로 리뷰를 좋아요 정보와 함께 조회한다")
    void findWithLikeReviewsById() {
        //given
        review.executeLike(user);

        //when
        Review result = reviewFindService.findWithLikeReviewsById(review.getId());

        //then
        assertThat(result).isEqualTo(review);
    }
}
