package com.inq.wishhair.wesharewishhair.photo.domain;

import com.inq.wishhair.wesharewishhair.global.base.RepositoryTest;
import com.inq.wishhair.wesharewishhair.global.fixture.ReviewFixture;
import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.inq.wishhair.wesharewishhair.global.fixture.ReviewFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

public class PhotoRepositoryTest extends RepositoryTest {

    private final Review[] reviews = new Review[values().length];

    @BeforeEach
    void setUp() {
        //given
        User user = userRepository.save(UserFixture.B.toEntity());
        for (int i = 0; i < values().length; i++) {
            ReviewFixture fixture = values()[i];
            reviews[i] = fixture.toEntity(user, null);
        }
    }

    @Test
    @DisplayName("입력받은 리뷰를 참조하는 사진을 모두 삭제한다")
    void deleteAllByReview() {
        //given
        saveReview(List.of(0));

        //when
        photoRepository.deleteAllByReview(reviews[0].getId());

        //then
        List<Photo> all = photoRepository.findAll();
        assertThat(all).isEmpty();
    }

    @Test
    @DisplayName("입력받은 리뷰들을 참조하는 사진을 모두 삭제한다")
    void deleteAllByWriter() {
        //given
        saveReview(List.of(0, 1, 2, 3, 4));

        //when
        photoRepository.deleteAllByReviews(List.of(
                reviews[0].getId(),
                reviews[1].getId(),
                reviews[2].getId(),
                reviews[3].getId(),
                reviews[4].getId()
        ));

        //then
        List<Photo> all = photoRepository.findAll();
        assertThat(all).isEmpty();
    }

    private void saveReview(List<Integer> indexes) {
        indexes.forEach(index -> reviewRepository.save(reviews[index]));
    }
}
