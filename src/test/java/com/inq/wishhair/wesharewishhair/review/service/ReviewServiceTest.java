package com.inq.wishhair.wesharewishhair.review.service;

import com.inq.wishhair.wesharewishhair.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.fixture.ReviewFixture;
import com.inq.wishhair.wesharewishhair.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.review.controller.dto.request.ReviewCreateRequest;
import com.inq.wishhair.wesharewishhair.review.controller.utils.ReviewCreateRequestUtils;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Optional;

import static com.inq.wishhair.wesharewishhair.fixture.ReviewFixture.*;
import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("ReviewServiceTest - SpringBootTest")
public class ReviewServiceTest extends ServiceTest {

    @Autowired
    private ReviewService reviewService;

    private User user;
    private HairStyle hairStyle;

    @BeforeEach
    void setUp() {
        user = userRepository.save(UserFixture.B.toEntity());
        hairStyle = hairStyleRepository.save(HairStyleFixture.A.toEntity());
    }

    @Nested
    @DisplayName("리뷰를 생성한다")
    class createReview {
        @Test
        @DisplayName("사진이 있는 리뷰를 생성한다")
        void test1() throws IOException {
            //given
            ReviewCreateRequest request = ReviewCreateRequestUtils.createRequest(A, hairStyle.getId());

            //when
            Long reviewId = reviewService.createReview(request, user.getId());

            //then
            Optional<Review> result = reviewRepository.findById(reviewId);
            assertAll(
                    () -> assertThat(result).isPresent(),
                    () -> {
                        Review review = result.get();
                        assertThat(review.getUser()).isEqualTo(user);
                        assertThat(review.getHairStyle()).isEqualTo(hairStyle);
                        assertThat(review.getContents()).isEqualTo(A.getContents());
                        assertThat(review.getScore()).isEqualTo(A.getScore());
                        assertThat(review.getPhotos()).hasSize(A.getOriginalFilenames().size());
                    }
            );
        }

        @Test
        @DisplayName("사진이 없는 리뷰를 생성한다")
        void test2() throws IOException {
            //given
            ReviewCreateRequest request = ReviewCreateRequestUtils.createRequest(C, hairStyle.getId());

            //when
            Long reviewId = reviewService.createReview(request, user.getId());

            //then
            Optional<Review> result = reviewRepository.findById(reviewId);
            assertAll(
                    () -> assertThat(result).isPresent(),
                    () -> {
                        Review review = result.get();
                        assertThat(review.getUser()).isEqualTo(user);
                        assertThat(review.getHairStyle()).isEqualTo(hairStyle);
                        assertThat(review.getContents()).isEqualTo(C.getContents());
                        assertThat(review.getScore()).isEqualTo(C.getScore());
                        assertThat(review.getPhotos()).hasSize(C.getOriginalFilenames().size());
                    }
            );
        }
    }
}
