package com.inq.wishhair.wesharewishhair.review.domain.likereview;

import com.inq.wishhair.wesharewishhair.global.fixture.ReviewFixture;
import com.inq.wishhair.wesharewishhair.global.base.RepositoryTest;
import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.domain.ReviewRepository;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Review-LikeReviewRepositoryTest - DataJpaTest")
public class LikeReviewRepositoryTest extends RepositoryTest {

    private final Long userId = 1L;
    private final Long reviewId = 1L;

    @BeforeEach
    void setUp() {
        //given
        likeReviewRepository.save(LikeReview.addLike(userId, reviewId));
    }

    @Test
    @DisplayName("입력받은 리뷰를 참조하는 좋아요를 삭제한다")
    void deleteByReview() {
        //when
        likeReviewRepository.deleteAllByReview(reviewId);

        //then
        List<LikeReview> result = likeReviewRepository.findAll();
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("리뷰 아이디와 사용자 아이디로 좋아요를 삭제한다")
    void deleteByUserIdAndReviewId() {
        //when
        likeReviewRepository.deleteByUserIdAndReviewId(userId, reviewId);

        //then
        List<LikeReview> result = likeReviewRepository.findAll();
        assertThat(result).isEmpty();
    }

    @Nested
    @DisplayName("리뷰와 사용자 아이디로 좋아요 존재여부를 확인한다")
    class existByUserAndReview {
        @Test
        @DisplayName("좋아요가 존재하여 true 를 응답한다")
        void exist() {
            //when
            boolean result = likeReviewRepository.existsByUserIdAndReviewId(userId, reviewId);

            //then
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("좋아요가 존재하지 않아 false 를 응답한다")
        void notExist() {
            //when
            boolean result = likeReviewRepository.existsByUserIdAndReviewId(userId, 2L);

            //then
            assertThat(result).isFalse();
        }
    }
}
