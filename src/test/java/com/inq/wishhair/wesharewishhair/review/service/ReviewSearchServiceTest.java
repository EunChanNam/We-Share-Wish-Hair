package com.inq.wishhair.wesharewishhair.review.service;

import com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.global.fixture.ReviewFixture;
import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.global.dto.response.PagedResponse;
import com.inq.wishhair.wesharewishhair.global.dto.response.ResponseWrapper;
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
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("ReviewFindServiceTest - SpringBootTest")
public class ReviewSearchServiceTest extends ServiceTest {

    @Autowired
    private ReviewSearchService reviewSearchService;

    private final List<Review> reviews = new ArrayList<>();
    private User user;

    @BeforeEach
    void setUp() {
        //given
        user = userRepository.save(UserFixture.A.toEntity());
        HairStyle hairStyle = hairStyleSearchRepository.save(HairStyleFixture.A.toEntity());

        for (ReviewFixture fixture : ReviewFixture.values()) {
            reviews.add(fixture.toEntity(user, hairStyle));
        }
    }

    @Nested
    @DisplayName("전체 리뷰를 조회한다")
    class findPagedReview {
        @Test
        @DisplayName("전체 리뷰를 좋아요 수로 정렬하여 조회한다")
        void orderByLikes() {
            //given
            saveReview(List.of(1, 4, 5), List.of(now(), now(), now()));

            User user1 = userRepository.save(UserFixture.B.toEntity());
            User user2 = userRepository.save(UserFixture.C.toEntity());

            addLikes(user, List.of(1, 4, 5));
            addLikes(user1, List.of(4, 5));
            addLikes(user2, List.of(5));

            Pageable pageable = DefaultPageableUtils.getLikeDescPageable(3);

            //when
            PagedResponse<ReviewResponse> result = reviewSearchService.findPagedReviews(pageable);

            //then
            assertThat(result.getPaging().hasNext()).isFalse();
            assertReviewResponseMatch(result.getResult(),
                    List.of(reviews.get(5), reviews.get(4), reviews.get(1)));
        }

        @Test
        @DisplayName("전체 리뷰를 최신 날짜 순으로 정렬해서 조회한다")
        void orderByDate() {
            //given
            saveReview(List.of(1, 2, 3, 4, 5), List.of(now().minusMonths(2), now().minusMinutes(1), now().minusHours(1),
                    now().minusDays(1), now().minusMonths(1)));

            Pageable pageable = DefaultPageableUtils.getDateDescPageable(5);

            //when
            PagedResponse<ReviewResponse> result = reviewSearchService.findPagedReviews(pageable);

            //then
            assertThat(result.getPaging().hasNext()).isFalse();
            assertReviewResponseMatch(result.getResult(),
                    List.of(reviews.get(2), reviews.get(3), reviews.get(4), reviews.get(5), reviews.get(1)));

        }
    }

    @Nested
    @DisplayName("사용자가 좋아요한 리뷰를 조회한다")
    class findLikingReviews {

        @Test
        @DisplayName("좋아요 한 리뷰가 없으면 아무것도 조회되지 않는다")
        void doesNotExistResult() {
            //given
            saveReview(List.of(1, 2, 3), List.of(now(), now(), now()));

            Pageable pageable = DefaultPageableUtils.getDateDescPageable(3);

            //when
            PagedResponse<ReviewResponse> result = reviewSearchService.findLikingReviews(user.getId(), pageable);

            //then
            assertThat(result.getResult()).isEmpty();
        }

        @Test
        @DisplayName("좋아요한 리뷰를 최신 날짜 순으로 조회한다")
        void orderByDate() {
            //given
            saveReview(List.of(1, 2, 3, 4), List.of(now().minusMonths(1), now().minusMinutes(1), now(),
                    now().minusDays(1)));

            addLikes(user, List.of(1, 2, 3, 4));

            Pageable pageable = DefaultPageableUtils.getDateDescPageable(4);

            //when
            PagedResponse<ReviewResponse> result = reviewSearchService.findLikingReviews(user.getId(), pageable);

            //then
            assertThat(result.getPaging().hasNext()).isFalse();
            assertReviewResponseMatch(result.getResult(),
                    List.of(reviews.get(3), reviews.get(2), reviews.get(4), reviews.get(1)));
        }

    }

    @Nested
    @DisplayName("사용자가 작성한 리뷰를 조회한다")
    class findMyReviews {
        @Test
        @DisplayName("작성한 리뷰가 없으면 아무것도 조회되지 않는다")
        void doesNotExistResult() {
            //given
            User other = userRepository.save(UserFixture.B.toEntity());
            saveReview(List.of(1, 2, 3, 4), List.of(now(), now(), now(), now()));

            Pageable pageable = DefaultPageableUtils.getDateDescPageable(4);

            //when
            PagedResponse<ReviewResponse> result = reviewSearchService.findMyReviews(other.getId(), pageable);

            //then
            assertThat(result.getResult()).isEmpty();
        }

        @Test
        @DisplayName("작성한 리뷰를 최신 날짜 순으로 조회한다")
        void orderByDate() {
            //given
            saveReview(List.of(1, 2, 3, 4), List.of(now().minusMonths(1), now().minusMinutes(1), now(),
                    now().minusDays(1)));

            Pageable pageable = DefaultPageableUtils.getDateDescPageable(4);

            //when
            PagedResponse<ReviewResponse> result = reviewSearchService.findMyReviews(user.getId(), pageable);

            //then
            assertThat(result.getPaging().hasNext()).isFalse();
            assertReviewResponseMatch(result.getResult(),
                    List.of(reviews.get(3), reviews.get(2), reviews.get(4), reviews.get(1)));
        }
    }

    @Test
    @DisplayName("지난달에 작성된 리뷰를 좋아요 수로 정렬해서 조회한다")
    void findReviewOfMonth() {
        //given
        saveReview(List.of(1, 2, 4, 5), List.of(now().minusMonths(1), now(),
                now().minusMonths(1), now().minusMonths(1)));

        User user1 = userRepository.save(UserFixture.B.toEntity());
        User user2 = userRepository.save(UserFixture.C.toEntity());

        addLikes(user, List.of(4, 5));
        addLikes(user1, List.of(4, 5));
        addLikes(user2, List.of(5));

        //when
        ResponseWrapper<ReviewSimpleResponse> result = reviewSearchService.findReviewOfMonth();

        //then
        assertReviewSimpleResponseMatch(result.getResult(),
                List.of(reviews.get(5), reviews.get(4), reviews.get(1)));
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
            reviews.get(index).executeLike(user);
        }
    }

    private void assertReviewResponseMatch(List<ReviewResponse> responses, List<Review> expectedReviews) {
        assertThat(responses).hasSize(expectedReviews.size());

        int expectedSize = responses.size();

        for (int index = 0; index < expectedSize; index++) {
            ReviewResponse response = responses.get(index);
            Review expected = expectedReviews.get(index);

            assertAll(
                    () -> assertThat(response.getReviewId()).isEqualTo(expected.getId()),
                    () -> assertThat(response.getLikes()).isEqualTo(expected.getLikes()),
                    () -> assertThat(response.getContents()).isEqualTo(expected.getContentsValue()),
                    () -> assertThat(response.getScore()).isEqualTo(expected.getScore().getValue()),
                    () -> assertThat(response.getCreatedDate()).isEqualTo(expected.getCreatedDate()),
                    () -> assertThat(response.getHairStyleName()).isEqualTo(expected.getHairStyle().getName()),
                    () -> assertThat(response.getUserNickname()).isEqualTo(expected.getUser().getNicknameValue()),
                    () -> {
                        List<String> expectedTags = expected.getHairStyle().getHashTags().stream()
                                .map(HashTag::getDescription).toList();
                        List<String> resultTags = response.getHashTags().stream().map(HashTagResponse::getTag).toList();

                        expectedTags.forEach(tag -> assertThat(resultTags).contains(tag));
                    },
                    () -> assertThat(response.getPhotos()).hasSize(expected.getPhotos().size())
            );
        }
    }

    private void assertReviewSimpleResponseMatch(List<ReviewSimpleResponse> responses, List<Review> expectedReviews) {
        assertThat(responses).hasSize(expectedReviews.size());

        int expectedSize = responses.size();

        for (int index = 0; index < expectedSize; index++) {
            ReviewSimpleResponse response = responses.get(index);
            Review expected = expectedReviews.get(index);

            assertAll(
                    () -> assertThat(response.getReviewId()).isEqualTo(expected.getId()),
                    () -> assertThat(response.getContents()).isEqualTo(expected.getContentsValue()),
                    () -> assertThat(response.getHairStyleName()).isEqualTo(expected.getHairStyle().getName()),
                    () -> assertThat(response.getUserNickname()).isEqualTo(expected.getUser().getNicknameValue())
            );
        }
    }
}
