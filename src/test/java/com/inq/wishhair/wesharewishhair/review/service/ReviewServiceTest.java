package com.inq.wishhair.wesharewishhair.review.service;

import com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.review.controller.dto.request.ReviewCreateRequest;
import com.inq.wishhair.wesharewishhair.review.controller.utils.ReviewCreateRequestUtils;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.inq.wishhair.wesharewishhair.global.fixture.ReviewFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("ReviewServiceTest - SpringBootTest")
public class ReviewServiceTest extends ServiceTest {
    //todo 포인트 충전 테스트 추가 + 이벤트 테스트
    @Autowired
    private ReviewService reviewService;

    private User user;
    private HairStyle hairStyle;

    @BeforeEach
    void setUp() {
        user = userRepository.save(UserFixture.B.toEntity());
        hairStyle = hairStyleSearchRepository.save(HairStyleFixture.A.toEntity());
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
                        assertThat(review.getContentsValue()).isEqualTo(A.getContents());
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
                        assertThat(review.getContentsValue()).isEqualTo(C.getContents());
                        assertThat(review.getScore()).isEqualTo(C.getScore());
                        assertThat(review.getPhotos()).hasSize(C.getOriginalFilenames().size());
                    }
            );
        }
    }

    @Nested
    @DisplayName("리뷰를 삭제한다")
    class deleteReview {

        private Review review;

        @BeforeEach
        void setUp() {
            //given
            review = reviewRepository.save(B.toEntity(user, hairStyle));
        }

        @Test
        @DisplayName("리뷰 작성자가 아니어서 리뷰 삭제에 실패한다")
        void test3() {
            //given
            ErrorCode expectedError = ErrorCode.REVIEW_NOT_WRITER;

            //when, then
            assertThatThrownBy(() -> reviewService.deleteReview(review.getId(), 100L))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(expectedError.getMessage());
        }

        @Test
        @DisplayName("리뷰 삭제에 성공한다")
        void test4() {
            //when
            reviewService.deleteReview(review.getId(), user.getId());

            //then
            List<Review> result = reviewRepository.findAll();
            assertThat(result).isEmpty();
        }
    }
}
