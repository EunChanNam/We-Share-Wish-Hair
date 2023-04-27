package com.inq.wishhair.wesharewishhair.review.service;

import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.wishhair.WishHair;
import com.inq.wishhair.wesharewishhair.review.domain.likereview.LikeReview;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Review-LikeReviewServiceTest - SpringBootTest")
public class LikeReviewServiceTest extends ServiceTest {

    @Autowired
    private LikeReviewService likeReviewService;

    private final Long userId = 1L;
    private final Long reviewId = 1L;

    @Nested
    @DisplayName("좋아요를 실행한다")
    class executeLike {
        @Test
        @DisplayName("좋아요를 생성한다")
        void success() {
            //when
            likeReviewService.executeLike(reviewId, userId);

            //then
            List<LikeReview> all = likeReviewRepository.findAll();
            assertThat(all).hasSize(1);
        }

        @Test
        @DisplayName("이미 좋아요 한 리뷰에 좋아요를 실행해 실패한다")
        void fail() {
            //given
            likeReviewService.executeLike(reviewId, userId);

            //when, then
            assertThatThrownBy(() -> likeReviewService.executeLike(reviewId, userId))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(ErrorCode.REVIEW_ALREADY_LIKING.getMessage());
        }
    }

    @Nested
    @DisplayName("좋아요를 취소한다")
    class cancelLike {
        @Test
        @DisplayName("좋아요를 취소한다")
        void success() {
            //given
            likeReviewService.executeLike(reviewId, userId);

            //when
            likeReviewService.cancelLike(reviewId, userId);

            //then
            List<LikeReview> all = likeReviewRepository.findAll();
            assertThat(all).isEmpty();
        }

        @Test
        @DisplayName("좋아요 하지 않은 리뷰에 좋아요 취소를 실행해 실패한다")
        void fail() {
            //when, then
            assertThatThrownBy(() -> likeReviewService.cancelLike(reviewId, userId))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(ErrorCode.REVIEW_NOT_LIKING.getMessage());
        }
    }

    @Nested
    @DisplayName("리뷰와 사용자 아이디로 좋아요 존재여부를 확인한다")
    class checkIsLiking {
        @Test
        @DisplayName("좋아요가 존재하여 true 를 응답한다")
        void exist() {
            //given
            likeReviewRepository.save(LikeReview.addLike(userId, reviewId));

            //when
            boolean result = likeReviewService.checkIsLiking(userId, reviewId);

            //then
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("좋아요가 존재하지 않아 false 를 응답한다")
        void notExist() {
            //when
            boolean result = likeReviewService.checkIsLiking(userId, reviewId);

            //then
            assertThat(result).isFalse();
        }
    }
}
