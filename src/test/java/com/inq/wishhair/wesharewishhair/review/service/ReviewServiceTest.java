package com.inq.wishhair.wesharewishhair.review.service;

import com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.photo.service.PhotoService;
import com.inq.wishhair.wesharewishhair.photo.utils.PhotoStore;
import com.inq.wishhair.wesharewishhair.review.controller.dto.request.ReviewCreateRequest;
import com.inq.wishhair.wesharewishhair.review.controller.utils.ReviewCreateRequestUtils;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.util.List;

import static com.inq.wishhair.wesharewishhair.global.fixture.ReviewFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@DisplayName("ReviewServiceTest - SpringBootTest")
public class ReviewServiceTest extends ServiceTest {

    @Autowired
    private ReviewService reviewService;

    @MockBean
    private PhotoStore photoStore;

    private User user;
    private HairStyle hairStyle;

    @BeforeEach
    void setUp() {
        user = userRepository.save(UserFixture.B.toEntity());
        hairStyle = hairStyleRepository.save(HairStyleFixture.A.toEntity());
    }

    @Test
    @DisplayName("리뷰를 생성한다")
    void test1() throws IOException {
        //given
        ReviewCreateRequest request = ReviewCreateRequestUtils.createRequest(A, hairStyle.getId());
        given(photoStore.uploadFiles(request.getFiles())).willReturn(A.getStoreUrls());

        //when
        Long reviewId = reviewService.createReview(request, user.getId());

        //then
        Review result = reviewRepository.findWithPhotosById(reviewId).orElseThrow();
        assertAll(
                () -> assertThat(result.getWriter()).isEqualTo(user),
                () -> assertThat(result.getWriter()).isEqualTo(user),
                () -> assertThat(result.getHairStyle()).isEqualTo(hairStyle),
                () -> assertThat(result.getContentsValue()).isEqualTo(A.getContents()),
                () -> assertThat(result.getScore()).isEqualTo(A.getScore()),
                () -> assertThat(result.getPhotos()).hasSize(A.getStoreUrls().size())
        );
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
            doNothing().when(photoStore).deleteFiles(any());

            //then
            List<Review> result = reviewRepository.findAll();
            assertThat(result).isEmpty();
        }
    }
}
