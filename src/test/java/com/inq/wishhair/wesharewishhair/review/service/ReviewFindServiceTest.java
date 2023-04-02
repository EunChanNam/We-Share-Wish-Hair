package com.inq.wishhair.wesharewishhair.review.service;

import com.inq.wishhair.wesharewishhair.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.fixture.ReviewFixture;
import com.inq.wishhair.wesharewishhair.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.global.utils.PageableUtils;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.HashTag;
import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.HashTagResponse;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewResponse;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import static com.inq.wishhair.wesharewishhair.fixture.ReviewFixture.*;
import static com.inq.wishhair.wesharewishhair.fixture.ReviewFixture.A;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("ReviewFindServiceTest - SpringBootTest")
public class ReviewFindServiceTest extends ServiceTest {

    @Autowired
    private ReviewFindService reviewFindService;

    private Review review;
    private User user;
    private HairStyle hairStyle;
    private final Pageable pageable = PageableUtils.getDefaultPageable();

    @BeforeEach
    void setUp() {
        //given
        user = userRepository.save(UserFixture.B.toEntity());
        hairStyle = hairStyleRepository.save(HairStyleFixture.A.toEntity());
        review = reviewRepository.save(A.toEntity(user, hairStyle));
    }

    @Test
    @DisplayName("아이디로 리뷰를 조회한다")
    void findById() {
        //when
        Review result = reviewFindService.findById(review.getId());

        //then
        assertAll(
                () -> assertThat(result.getUser()).isEqualTo(user),
                () -> assertThat(result.getHairStyle()).isEqualTo(hairStyle),
                () -> assertThat(result.getScore()).isEqualTo(A.getScore()),
                () -> assertThat(result.getContents()).isEqualTo(A.getContents())
        );
    }

    @Test
    @DisplayName("전체 리뷰를 조회한다")
    void findPagedReviews() {
        //when
        Slice<ReviewResponse> result = reviewFindService.findPagedReviews(pageable);

        //then
        assertAll(
                () -> assertThat(result.getContent()).hasSize(1),
                () -> {
                    ReviewResponse response = result.getContent().get(0);
                    assertReviewResponse(response);
                }
        );
    }

    @Nested
    @DisplayName("사용자가 좋아요한 리뷰를 조회한다")
    class findLikingReviews {
        @Test
        @DisplayName("좋아요 한 리뷰가 없으면 아무것도 조회되지 않는다")
        void findLikingReviews1() {
            //when
            Slice<ReviewResponse> result = reviewFindService.findLikingReviews(user.getId(), pageable);

            //then
            assertThat(result.getContent()).isEmpty();
        }

        @Test
        @DisplayName("좋아요한 리뷰를 조회한다")
        void findLikingReviews2() {
            //given
            review.executeLike(user);

            //when
            Slice<ReviewResponse> result = reviewFindService.findLikingReviews(user.getId(), pageable);

            //then
            assertAll(
                    () -> assertThat(result.getContent()).hasSize(1),
                    () -> {
                        ReviewResponse response = result.getContent().get(0);
                        assertReviewResponse(response);
                    }
            );
        }
    }

    @Test
    @DisplayName("사용자가 작성한 리뷰를 조회한다")
    void findMyReviews() {
        //when
        Slice<ReviewResponse> result = reviewFindService.findMyReviews(user.getId(), pageable);

        //then
        assertAll(
                () -> assertThat(result.getContent()).hasSize(1),
                () -> {
                    ReviewResponse response = result.getContent().get(0);
                    assertReviewResponse(response);
                }
        );
    }

    private void assertReviewResponse(ReviewResponse response) {
        assertThat(response.getContents()).isEqualTo(A.getContents());
        assertThat(response.getScore()).isEqualTo(A.getScore().getValue());
        assertThat(response.getHairStyleName()).isEqualTo(hairStyle.getName());
        assertThat(response.getUserNickName()).isEqualTo(user.getNicknameValue());
        hairStyle.getHashTags().stream().map(HashTag::getDescription).toList()
                .forEach(tag -> assertThat(response.getHasTags()
                        .stream().map(HashTagResponse::getTag).toList())
                        .contains(tag));
    }
}
