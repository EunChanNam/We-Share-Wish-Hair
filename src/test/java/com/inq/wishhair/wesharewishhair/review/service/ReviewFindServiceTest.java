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
import org.springframework.test.annotation.Rollback;
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

    private final List<Review> reviews = new ArrayList<>();
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
            Slice<ReviewResponse> result = reviewFindService.findPagedReviews(pageable);

            //then
            assertAll(
                    () -> assertThat(result.getContent()).hasSize(3),
                    () -> assertThat(result.hasNext()).isFalse(),
                    () -> {
                        List<String> findContents = extractContents(result);
                        assertThat(findContents).containsExactly(
                                reviews.get(5).getContents(),
                                reviews.get(4).getContents(),
                                reviews.get(1).getContents());
                    },
                    () -> assertReviewResponseValues(result.getContent().get(0), reviews.get(5))
            );
        }

        @Test
        @DisplayName("전체 리뷰를 최신 날짜 순으로 정렬해서 조회한다")
        void orderByDate() {
            //given
            saveReview(List.of(1, 2, 3, 4, 5), List.of(now(), now().minusMinutes(1), now().minusHours(1),
                    now().minusDays(1), now().minusMonths(1)));

            Pageable pageable = DefaultPageableUtils.getDateAscPageable(5);

            //when
            Slice<ReviewResponse> result = reviewFindService.findPagedReviews(pageable);

            result.getContent().forEach(response -> System.out.println(response.getContents()));

            //then
            assertAll(
                    () -> assertThat(result.getContent()).hasSize(5),
                    () -> assertThat(result.hasNext()).isFalse(),
                    () -> {
                        List<String> findContents = extractContents(result);
                        assertThat(findContents).containsExactly(
                                reviews.get(1).getContents(),
                                reviews.get(2).getContents(),
                                reviews.get(3).getContents(),
                                reviews.get(4).getContents(),
                                reviews.get(5).getContents()
                        );
                    },
                    () -> assertReviewResponseValues(result.getContent().get(0), reviews.get(1))
            );

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

            Pageable pageable = DefaultPageableUtils.getDateAscPageable(3);

            //when
            Slice<ReviewResponse> result = reviewFindService.findLikingReviews(user.getId(), pageable);

            //then
            assertThat(result.getContent()).isEmpty();
        }

        @Test
        @DisplayName("좋아요한 리뷰를 최신 날짜 순으로 조회한다")
        void orderByDate() {
            //given
            saveReview(List.of(1, 2, 3, 4), List.of(now(), now().minusMinutes(1), now().minusHours(1),
                    now().minusDays(1)));

            addLikes(user, List.of(1, 2, 3, 4));

            Pageable pageable = DefaultPageableUtils.getDateAscPageable(4);

            //when
            Slice<ReviewResponse> result = reviewFindService.findLikingReviews(user.getId(), pageable);

            //then
            assertAll(
                    () -> assertThat(result.getContent()).hasSize(4),
                    () -> assertThat(result.hasNext()).isFalse(),
                    () -> {
                        List<String> findContents = extractContents(result);
                        assertThat(findContents).containsExactly(
                                reviews.get(1).getContents(),
                                reviews.get(2).getContents(),
                                reviews.get(3).getContents(),
                                reviews.get(4).getContents()
                        );
                    },
                    () -> assertReviewResponseValues(result.getContent().get(0), reviews.get(1))
            );
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

            Pageable pageable = DefaultPageableUtils.getDateAscPageable(4);

            //when
            Slice<ReviewResponse> result = reviewFindService.findMyReviews(other.getId(), pageable);

            //then
            assertThat(result.getContent()).isEmpty();
        }

        @Test
        @DisplayName("작성한 리뷰를 최신 날짜 순으로 조회한다")
        void orderByDate() {
            //given
            saveReview(List.of(1, 2, 3, 4), List.of(now(), now().minusMinutes(1), now().minusHours(1),
                    now().minusDays(1)));

            Pageable pageable = DefaultPageableUtils.getDateAscPageable(4);

            //when
            Slice<ReviewResponse> result = reviewFindService.findMyReviews(user.getId(), pageable);

            //then
            assertAll(
                    () -> assertThat(result.getContent()).hasSize(4),
                    () -> assertThat(result.hasNext()).isFalse(),
                    () -> {
                        List<String> findContents = extractContents(result);
                        assertThat(findContents).containsExactly(
                                reviews.get(1).getContents(),
                                reviews.get(2).getContents(),
                                reviews.get(3).getContents(),
                                reviews.get(4).getContents()
                        );
                    },
                    () -> assertReviewResponseValues(result.getContent().get(0), reviews.get(1))
            );
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
        List<ReviewSimpleResponse> result = reviewFindService.findReviewOfMonth();

        //then
        assertAll(
                () -> assertThat(result).hasSize(3),
                () -> assertThat(result.stream().map(ReviewSimpleResponse::getContents))
                        .containsExactly(
                                reviews.get(5).getContents(),
                                reviews.get(4).getContents(),
                                reviews.get(1).getContents()
                        ),
                () -> {
                    ReviewSimpleResponse response = result.get(0);
                    Review expected = reviews.get(5);
                    assertThat(response.getReviewId()).isEqualTo(expected.getId());
                    assertThat(response.getNickname()).isEqualTo(expected.getUser().getNicknameValue());
                    assertThat(response.getHairStyleName()).isEqualTo(expected.getHairStyle().getName());
                }
        );
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

    private List<String> extractContents(Slice<ReviewResponse> result) {
        return result.getContent().stream()
                .map(ReviewResponse::getContents).toList();
    }

    private void assertReviewResponseValues(ReviewResponse response, Review expected) {
        assertThat(response.getLikes()).isEqualTo(expected.getLikes());
        assertThat(response.getScore()).isEqualTo(expected.getScore().getValue());
        assertThat(response.getHairStyleName()).isEqualTo(expected.getHairStyle().getName());
        assertThat(response.getUserNickName()).isEqualTo(expected.getUser().getNicknameValue());

        List<String> expectedTags = expected.getHairStyle().getHashTags().stream()
                .map(HashTag::getDescription).toList();
        List<String> resultTags = response.getHasTags().stream().map(HashTagResponse::getTag).toList();

        expectedTags.forEach(tag -> assertThat(resultTags).contains(tag));
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
