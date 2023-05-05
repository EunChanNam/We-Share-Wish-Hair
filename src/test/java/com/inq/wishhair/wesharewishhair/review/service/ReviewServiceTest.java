package com.inq.wishhair.wesharewishhair.review.service;

import com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.photo.domain.Photo;
import com.inq.wishhair.wesharewishhair.photo.service.PhotoService;
import com.inq.wishhair.wesharewishhair.photo.utils.PhotoStore;
import com.inq.wishhair.wesharewishhair.review.controller.dto.request.ReviewCreateRequest;
import com.inq.wishhair.wesharewishhair.review.controller.dto.request.ReviewUpdateRequest;
import com.inq.wishhair.wesharewishhair.review.controller.utils.ReviewCreateRequestUtils;
import com.inq.wishhair.wesharewishhair.review.controller.utils.ReviewUpdateRequestUtils;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.event.PointChargeEvent;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

import java.io.IOException;
import java.util.List;

import static com.inq.wishhair.wesharewishhair.global.fixture.ReviewFixture.*;
import static com.inq.wishhair.wesharewishhair.review.controller.utils.ReviewUpdateRequestUtils.request;
import static com.inq.wishhair.wesharewishhair.review.controller.utils.ReviewUpdateRequestUtils.wrongContentsRequest;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@RecordApplicationEvents
@DisplayName("ReviewServiceTest - SpringBootTest")
public class ReviewServiceTest extends ServiceTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ApplicationEvents events;

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

        int count = (int) events.stream(PointChargeEvent.class).count();
        assertThat(count).isEqualTo(1);
    }

    @Nested
    @DisplayName("리뷰를 삭제 서비스 테스트")
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
            //given
            List<String> storeUrls = review.getPhotos().stream().map(Photo::getStoreUrl).toList();
            doNothing().when(photoStore).deleteFiles(storeUrls);

            //when
            reviewService.deleteReview(review.getId(), user.getId());

            //then
            List<Review> result = reviewRepository.findAll();
            assertThat(result).isEmpty();
        }
    }

    @Nested
    @DisplayName("리뷰 수정 서비스 테스트")
    class updateReview {
        private Review review;

        @BeforeEach
        void setUp() {
            //given
            review = reviewRepository.save(B.toEntity(user, hairStyle));
        }

        @Test
        @DisplayName("리뷰 수정을 성공한다")
        void success() throws IOException {
            //given
            ReviewUpdateRequest request = request(review.getId(), A);
            given(photoStore.uploadFiles(request.getFiles())).willReturn(A.getStoreUrls());

            //when, then
            assertDoesNotThrow(() -> reviewService.updateReview(request, user.getId()));
        }

        @Test
        @DisplayName("수정하는 Contents 가 길이 형식이 맞지 않아 실패한다")
        void failByContents() throws IOException {
            //given
            ReviewUpdateRequest request = wrongContentsRequest(review.getId(), A);

            //when, then
            assertThatThrownBy(() -> reviewService.updateReview(request, user.getId()))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(ErrorCode.CONTENTS_INVALID_LENGTH.getMessage());
        }

        @Test
        @DisplayName("작성자가 아니여서 실패한다")
        void failByNotWriter() throws IOException {
            //given
            final Long WRONG_USER_ID = 99L;
            ReviewUpdateRequest request = request(review.getId(), A);

            //when, then
            assertThatThrownBy(() -> reviewService.updateReview(request, WRONG_USER_ID))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(ErrorCode.REVIEW_NOT_WRITER.getMessage());
        }
    }
}
