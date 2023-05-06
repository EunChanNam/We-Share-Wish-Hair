package com.inq.wishhair.wesharewishhair.review.domain;

import com.inq.wishhair.wesharewishhair.global.base.RepositoryTest;
import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.inq.wishhair.wesharewishhair.global.fixture.ReviewFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ReviewRepositoryTest - DataJpaTest")
public class ReviewRepositoryTest extends RepositoryTest {

    private User user;
    private final Review[] reviews = new Review[values().length];

    @BeforeEach
    void setUp() {
        //given
        user = userRepository.save(UserFixture.A.toEntity());
        for (int i = 0; i < values().length; i++) {
            reviews[i] = reviewRepository.save(values()[i].toEntity(user, null));

        }
    }

    @Test
    @DisplayName("리뷰를 아이디로 사진 정보와 함께 조회한다")
    void findWithPhotosById() {
        //when
        Review result = reviewRepository.findWithPhotosById(reviews[0].getId()).orElseThrow();

        //then
        assertThat(result).isEqualTo(reviews[0]);
    }

    @Test
    @DisplayName("작성자 아이디로 사진 정보와 함께 조회한다")
    void findWithPhotosByUserId() {
        //when
        List<Review> result = reviewRepository.findWithPhotosByUserId(user.getId());

        //then
        assertThat(result).containsAll(Arrays.asList(reviews));
    }
}
