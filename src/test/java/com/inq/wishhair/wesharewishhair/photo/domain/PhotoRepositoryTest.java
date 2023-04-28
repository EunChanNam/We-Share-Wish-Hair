package com.inq.wishhair.wesharewishhair.photo.domain;

import com.inq.wishhair.wesharewishhair.global.base.RepositoryTest;
import com.inq.wishhair.wesharewishhair.global.fixture.ReviewFixture;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PhotoRepositoryTest extends RepositoryTest {

    @Test
    @DisplayName("입력받은 리뷰를 참조하는 사진을 모두 삭제한다")
    void deleteAllByReview() {
        //given
        Review review = reviewRepository.save(ReviewFixture.A.toEntity(null, null));

        photoRepository.save(Photo.createReviewPhoto("hello.png", review));
        photoRepository.save(Photo.createReviewPhoto("hello1.png", review));

        //when
        photoRepository.deleteAllByReview(review.getId());

        //then
        List<Photo> all = photoRepository.findAll();
        assertThat(all).isEmpty();
    }
}
