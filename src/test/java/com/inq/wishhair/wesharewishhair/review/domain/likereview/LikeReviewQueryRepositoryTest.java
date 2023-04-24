package com.inq.wishhair.wesharewishhair.review.domain.likereview;

import com.inq.wishhair.wesharewishhair.global.base.RepositoryTest;
import com.inq.wishhair.wesharewishhair.global.fixture.ReviewFixture;
import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("review-LikeReviewQueryRepositoryTest - DataJpaTest")
public class LikeReviewQueryRepositoryTest extends RepositoryTest {

    @Autowired
    private LikeReviewRepository likeReviewRepository;

    @Nested
    @DisplayName("리뷰와 사용자 아이디로 좋아요 존재여부를 확인한다")
    class existByUserAndReview {
        @Test
        @DisplayName("좋아요가 존재하여 true 를 응답한다")
        void exist() {
            //given
            User user = userRepository.save(UserFixture.B.toEntity());
            Review review = reviewRepository.save(ReviewFixture.A.toEntity(user, null));
            likeReviewRepository.save(LikeReview.createLikeReview(user, review));

            //when
            boolean result = likeReviewRepository.existByUserAndReview(user.getId(), review.getId());

            //then
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("좋아요가 존재하지 않아 false 를 응답한다")
        void notExist() {
            //when
            boolean result = likeReviewRepository.existByUserAndReview(1L, 1L);

            //then
            assertThat(result).isFalse();
        }
    }
}
