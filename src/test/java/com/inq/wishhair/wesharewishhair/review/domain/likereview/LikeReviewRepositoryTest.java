package com.inq.wishhair.wesharewishhair.review.domain.likereview;

import com.inq.wishhair.wesharewishhair.global.base.RepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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

    @Test
    @DisplayName("입력받은 리뷰 이이디들을 참조하는 모든 좋아요를 삭제한다")
    void deleteAllByReviews() {
        //given
        final List<Long> reviewIds = new ArrayList<>(List.of(2L, 3L, 4L));
        final Long userId2 = 2L;
        reviewIds.forEach(reviewId -> {
            likeReviewRepository.save(LikeReview.addLike(userId, reviewId));
            likeReviewRepository.save(LikeReview.addLike(userId2, reviewId));
        });
        reviewIds.add(reviewId);

        //when
        likeReviewRepository.deleteAllByReviews(reviewIds);

        //then
        List<LikeReview> result = likeReviewRepository.findAll();
        assertThat(result).isEmpty();
    }
}
