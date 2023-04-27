package com.inq.wishhair.wesharewishhair.review.infra.query;

import com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.global.base.RepositoryTest;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.domain.ReviewRepository;
import com.inq.wishhair.wesharewishhair.review.infra.query.dto.response.ReviewQueryResponse;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;

import static com.inq.wishhair.wesharewishhair.global.fixture.ReviewFixture.*;
import static com.inq.wishhair.wesharewishhair.global.utils.DefaultPageableUtils.getLikeDescPageable;
import static com.inq.wishhair.wesharewishhair.global.utils.PageableUtils.getDefaultPageable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("ReviewFindRepository DataJpaTest")
public class ReviewQueryRepositoryTest extends RepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private HairStyle hairStyle;
    private Review review;
    private final Pageable pageable = getDefaultPageable();

    @BeforeEach
    void setUp() {
        //given
        user = userRepository.save(UserFixture.B.toEntity());
        hairStyle = hairStyleRepository.save(HairStyleFixture.A.toEntity());
        review = A.toEntity(user, hairStyle);
        ReflectionTestUtils.setField(review, "createdDate", LocalDateTime.now().minusMonths(1));
        review = reviewRepository.save(review);
    }

    @Test
    @DisplayName("전체리뷰를 조회한다")
    void findReviewByPaging() {
        //when
        Slice<ReviewQueryResponse> result = reviewRepository.findReviewByPaging(getLikeDescPageable(3));

        //then
        List<ReviewQueryResponse> content = result.getContent();
        Review actual = content.get(0).getReview();
        assertAll(
                () -> assertThat(content).hasSize(1),
                () -> assertThat(actual).isEqualTo(review),
                () -> assertThat(actual.getWriter()).isEqualTo(user),
                () -> assertThat(actual.getHairStyle()).isEqualTo(hairStyle),
                () -> assertThat(actual.getPhotos()).hasSize(A.getStoreUrls().size()),
                () -> assertThat(content.get(0).getLikes()).isZero()
        );
    }

    @Test
    @DisplayName("사용자가 좋아요한 리뷰를 조회한다")
    void findReviewByLike() {
        //given
        Review review2 = B.toEntity(user, hairStyle);
        review2.executeLike(user);
        reviewRepository.save(review2);

        //when
        Slice<ReviewQueryResponse> result = reviewRepository.findReviewByLike(user.getId(), pageable);

        //then
        List<ReviewQueryResponse> content = result.getContent();
        Review actual = content.get(0).getReview();
        assertAll(
                () -> assertThat(content).hasSize(1),
                () -> assertThat(actual).isEqualTo(review2),
                () -> assertThat(actual.getWriter()).isEqualTo(user),
                () -> assertThat(actual.getHairStyle()).isEqualTo(hairStyle),
                () -> assertThat(actual.getPhotos()).hasSize(B.getStoreUrls().size()),
                () -> assertThat(content.get(0).getLikes()).isEqualTo(1)
        );
    }

    @Test
    @DisplayName("사용자가 작성한 리뷰를 조회한다")
    void findReviewByUser() {
        //when
        Slice<ReviewQueryResponse> result = reviewRepository.findReviewByUser(user.getId(), pageable);

        //then
        List<ReviewQueryResponse> content = result.getContent();
        Review actual = content.get(0).getReview();
        assertAll(
                () -> assertThat(content).hasSize(1),
                () -> assertThat(actual).isEqualTo(review),
                () -> assertThat(actual.getWriter()).isEqualTo(user),
                () -> assertThat(actual.getHairStyle()).isEqualTo(hairStyle),
                () -> assertThat(actual.getPhotos()).hasSize(A.getStoreUrls().size()),
                () -> assertThat(content.get(0).getLikes()).isZero()
        );
    }

    @Test
    @DisplayName("지난달에 작성된 리뷰를 조회한다")
    void findReviewByCreatedDate() {
        //when
        List<Review> result = reviewRepository.findReviewByCreatedDate();

        //then
        assertAll(
                () -> assertThat(result).hasSize(1),
                () -> assertThat(result.get(0)).isEqualTo(review),
                () -> assertThat(result.get(0).getWriter()).isEqualTo(user),
                () -> assertThat(result.get(0).getHairStyle()).isEqualTo(hairStyle),
                () -> assertThat(result.get(0).getPhotos()).hasSize(A.getStoreUrls().size())
        );
    }
}
