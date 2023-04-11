package com.inq.wishhair.wesharewishhair.review.domain.likereview;

import com.inq.wishhair.wesharewishhair.global.fixture.ReviewFixture;
import com.inq.wishhair.wesharewishhair.global.base.RepositoryTest;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.domain.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Review-LikeReviewRepositoryTest - DataJpaTest")
public class LikeReviewRepositoryTest extends RepositoryTest {

    @Autowired
    private LikeReviewRepository likeReviewRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    private Review review;

    @BeforeEach
    void setUp() {
        //given
        review = reviewRepository.save(ReviewFixture.A.toEntity(null, null));
        likeReviewRepository.save(LikeReview.createLikeReview(null, review));
    }

    @Test
    @DisplayName("입력받은 리뷰를 참조하는 LikeReview 를 삭제한다")
    void deleteByReview() {
        //when
        likeReviewRepository.deleteAllByReview(review.getId());

        //then
        List<LikeReview> result = likeReviewRepository.findAll();
        assertThat(result).isEmpty();
    }
}
