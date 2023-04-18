package com.inq.wishhair.wesharewishhair.review.domain;

import com.inq.wishhair.wesharewishhair.global.base.RepositoryTest;
import com.inq.wishhair.wesharewishhair.global.fixture.ReviewFixture;
import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("ReviewRepositoryTest - DataJpaTest")
public class ReviewRepositoryTest extends RepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private Review review;

    @BeforeEach
    void setUp() {
        //given
        user = userRepository.save(UserFixture.A.toEntity());
        review = reviewRepository.save(ReviewFixture.A.toEntity(user, null));
    }

    @Test
    @DisplayName("리뷰를 아이디로 유저 정보와 함께 조회한다")
    void findWithUserById() {
        //when
        Review result = reviewRepository.findById(review.getId()).orElseThrow();

        //then
        assertAll(
                () -> assertThat(result).isEqualTo(review),
                () -> assertThat(result.getUser()).isEqualTo(user)
        );
    }

    @Test
    @DisplayName("리뷰를 아이디로 좋아요 정보와 함께 조회한다")
    void findWithLikeReviewsById() {
        //given
        review.executeLike(user);

        //when
        Review result = reviewRepository.findWithLockById(review.getId()).orElseThrow();

        //then
        assertAll(
                () -> assertThat(result).isEqualTo(review),
                () -> assertThat(result.getLikeReviews()).hasSize(1)
        );
    }
}
