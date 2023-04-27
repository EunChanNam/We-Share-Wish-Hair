package com.inq.wishhair.wesharewishhair.review.service;

import com.inq.wishhair.wesharewishhair.global.fixture.ReviewFixture;
import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.domain.likereview.LikeReview;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Review-LikeReviewServiceTest - SpringBootTest")
public class LikeReviewServiceTest extends ServiceTest {

    @Autowired
    private LikeReviewService likeReviewService;

    private User user;
    private Review review;

    @BeforeEach
    void setUp() {
        //given
        user = userRepository.save(UserFixture.B.toEntity());
        review = reviewRepository.save(ReviewFixture.A.toEntity(user, null));
    }

    @Nested
    @DisplayName("좋아요를 실행한다")
    class likeReview {
        @Test
        @DisplayName("좋아요를 하지 않은 리뷰에 좋아요를 실행해 좋아요가 생성된다")
        void likeReview1() {
            //when
            likeReviewService.executeLike(review.getId(), user.getId());

            //then
            List<LikeReview> all = likeReviewRepository.findAll();
            assertAll(
                    () -> assertThat(review.getLikeReviews()).hasSize(1),
                    () -> assertThat(review.getLikes()).isEqualTo(1),
                    () -> assertThat(all).hasSize(1)
            );
        }

        @Test
        @DisplayName("좋아요를 한 리뷰에 좋아요를 실행해 좋아요가 취소된다")
        void likeReview2() {
            //given
            likeReviewService.executeLike(review.getId(), user.getId());

            //when
            em.flush();
            likeReviewService.executeLike(review.getId(), user.getId());

            //then
            List<LikeReview> all = likeReviewRepository.findAll();
            assertAll(
                    () -> assertThat(review.getLikeReviews()).isEmpty(),
                    () -> assertThat(review.getLikes()).isZero(),
                    () -> assertThat(all).isEmpty()
            );
        }
    }

    @Nested
    @DisplayName("리뷰와 사용자 아이디로 좋아요 존재여부를 확인한다")
    class checkIsLiking {
        @Test
        @DisplayName("좋아요가 존재하여 true 를 응답한다")
        void exist() {
            //given
            likeReviewRepository.save(LikeReview.addLike(user, review));

            //when
            boolean result = likeReviewService.checkIsLiking(user.getId(), review.getId());

            //then
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("좋아요가 존재하지 않아 false 를 응답한다")
        void notExist() {
            //when
            boolean result = likeReviewService.checkIsLiking(user.getId(), review.getId());

            //then
            assertThat(result).isFalse();
        }
    }
}
