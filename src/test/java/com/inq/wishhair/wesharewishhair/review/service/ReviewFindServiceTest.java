package com.inq.wishhair.wesharewishhair.review.service;

import com.inq.wishhair.wesharewishhair.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.fixture.ReviewFixture;
import com.inq.wishhair.wesharewishhair.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.global.utils.DefaultPageableUtils;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.HashTag;
import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.HashTagResponse;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewResponse;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewSimpleResponse;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.inq.wishhair.wesharewishhair.fixture.ReviewFixture.A;
import static java.time.LocalDateTime.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("ReviewFindServiceTest - SpringBootTest")
public class ReviewFindServiceTest extends ServiceTest {

    @Autowired
    private ReviewFindService reviewFindService;

    private List<Review> reviews = new ArrayList<>();
    private User user;
    private HairStyle hairStyle;

    @BeforeEach
    void setUp() {
        //given
        user = userRepository.save(UserFixture.A.toEntity());
        hairStyle = hairStyleRepository.save(HairStyleFixture.A.toEntity());

        for (ReviewFixture fixture : ReviewFixture.values()) {
            reviews.add(fixture.toEntity(user, hairStyle));
        }
    }

    @Test
    @DisplayName("아이디로 리뷰를 조회한다")
    void findById() {
        //given
        saveReview(List.of(0), List.of(now()));
        Review review = reviews.get(0);

        //when
        Review result = reviewFindService.findById(review.getId());

        //then
        assertAll(
                () -> assertThat(result.getUser()).isEqualTo(user),
                () -> assertThat(result.getHairStyle()).isEqualTo(hairStyle),
                () -> assertThat(result.getScore()).isEqualTo(review.getScore()),
                () -> assertThat(result.getContents()).isEqualTo(review.getContents())
        );
    }

    @Nested
    @DisplayName("전체 리뷰를 조회한다")
    class findPagedReview {
        @Test
        @DisplayName("전체 리뷰를 좋아요 수로 정렬하여 조회한다")
        void findPagedReviews() {
            //given
            saveReview(List.of(1, 4, 5), List.of(now(), now(), now()));

            User user1 = userRepository.save(UserFixture.B.toEntity());
            User user2 = userRepository.save(UserFixture.C.toEntity());

            addLikes(user, List.of(1, 4, 5));
            addLikes(user1, List.of(4, 5));
            addLikes(user2, List.of(5));

            Pageable pageable = DefaultPageableUtils.getLikeDescPageable(3);

            //when
            Slice<ReviewResponse> result = reviewFindService.findPagedReviews(pageable);

            //then
            assertAll(
                    () -> assertThat(result.getContent()).hasSize(3),
                    () -> assertThat(result.hasNext()).isFalse(),
                    () -> {
                        List<String> findContents = result.getContent().stream()
                                .map(ReviewResponse::getContents).toList();
                        assertThat(findContents).containsExactly(
                                reviews.get(5).getContents(),
                                reviews.get(4).getContents(),
                                reviews.get(1).getContents());
                    }
            );
        }


    }

    @Nested
    @DisplayName("사용자가 좋아요한 리뷰를 조회한다")
    class findLikingReviews {
        @Test
        @DisplayName("좋아요 한 리뷰가 없으면 아무것도 조회되지 않는다")
        void findLikingReviews1() {
            //when
            Slice<ReviewResponse> result = reviewFindService.findLikingReviews(user.getId(), null);

            //then
            assertThat(result.getContent()).isEmpty();
        }

        @Test
        @DisplayName("좋아요한 리뷰를 조회한다")
        void findLikingReviews2() {

            //when
            Slice<ReviewResponse> result = reviewFindService.findLikingReviews(user.getId(), null);

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
        Slice<ReviewResponse> result = reviewFindService.findMyReviews(user.getId(), null);

        //then
        assertAll(
                () -> assertThat(result.getContent()).hasSize(1),
                () -> {
                    ReviewResponse response = result.getContent().get(0);
                    assertReviewResponse(response);
                }
        );
    }

    @Test
    @DisplayName("지난달에 작성된 리뷰를 조회한다")
    void findReviewOfMonth() {
        //when
        List<ReviewSimpleResponse> result = reviewFindService.findReviewOfMonth();

        //then
        assertThat(result).isEmpty();
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

    private void saveReview(List<Integer> indexes, List<LocalDateTime> times) {
        for (int i = 0; i < indexes.size(); i++) {
            int index = indexes.get(i);

            Review review = reviews.get(index);
            LocalDateTime time = times.get(i);

            ReflectionTestUtils.setField(review, "createdDate", time);
            reviewRepository.save(review);
        }
    }

    private void addLikes(User user, List<Integer> indexes) {
        for (int index : indexes) {
            executeLike(reviews.get(index), user);
        }
    }

    private void executeLike(Review review, User user) {
        review.executeLike(user);
    }
}
