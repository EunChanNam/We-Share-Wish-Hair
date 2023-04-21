package com.inq.wishhair.wesharewishhair.review.domain;

import com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.global.base.RepositoryTest;
import com.inq.wishhair.wesharewishhair.global.utils.PageableUtils;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyleSearchRepository;
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
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("ReviewFindRepository DataJpaTest")
public class ReviewSearchRepositoryTest extends RepositoryTest {

    @Autowired
    private ReviewSearchRepository reviewSearchRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HairStyleSearchRepository hairStyleSearchRepository;

    private User user;
    private HairStyle hairStyle;
    private Review review;
    private final Pageable pageable = PageableUtils.getDefaultPageable();

    @BeforeEach
    void setUp() {
        //given
        user = userRepository.save(UserFixture.B.toEntity());
        hairStyle = hairStyleSearchRepository.save(HairStyleFixture.A.toEntity());
        review = A.toEntity(user, hairStyle);
        ReflectionTestUtils.setField(review, "createdDate", LocalDateTime.now().minusMonths(1));
        review = reviewSearchRepository.save(review);
    }

    @Test
    @DisplayName("전체리뷰를 조회한다")
    void findReviewByPaging() {
        //when
        Slice<Review> result = reviewSearchRepository.findReviewByPaging(pageable);

        //then
        assertAll(
                () -> assertThat(result.getContent()).hasSize(1),
                () -> assertThat(result.getContent().get(0)).isEqualTo(review),
                () -> assertThat(result.getContent().get(0).getUser()).isEqualTo(user),
                () -> assertThat(result.getContent().get(0).getHairStyle()).isEqualTo(hairStyle),
                () -> assertThat(result.getContent().get(0).getPhotos()).hasSize(A.getOriginalFilenames().size())
        );
    }

    @Test
    @DisplayName("사용자가 좋아요한 리뷰를 조회한다")
    void findReviewByLike() {
        //given
        Review review2 = B.toEntity(user, hairStyle);
        review2.executeLike(user);
        reviewSearchRepository.save(review2);

        //when
        Slice<Review> result = reviewSearchRepository.findReviewByLike(user.getId(), pageable);

        //then
        assertAll(
                () -> assertThat(result.getContent()).hasSize(1),
                () -> assertThat(result.getContent().get(0)).isEqualTo(review2),
                () -> assertThat(result.getContent().get(0).getUser()).isEqualTo(user),
                () -> assertThat(result.getContent().get(0).getHairStyle()).isEqualTo(hairStyle)
        );
    }

    @Test
    @DisplayName("사용자가 작성한 리뷰를 조회한다")
    void findReviewByUser() {
        //when
        Slice<Review> result = reviewSearchRepository.findReviewByUser(user.getId(), pageable);

        //then
        assertAll(
                () -> assertThat(result.getContent()).hasSize(1),
                () -> assertThat(result.getContent().get(0)).isEqualTo(review),
                () -> assertThat(result.getContent().get(0).getUser()).isEqualTo(user),
                () -> assertThat(result.getContent().get(0).getHairStyle()).isEqualTo(hairStyle),
                () -> assertThat(result.getContent().get(0).getPhotos()).hasSize(A.getOriginalFilenames().size())
        );
    }

    @Test
    @DisplayName("지난달에 작성된 리뷰를 조회한다")
    void findReviewByCreatedDate() {
        //when
        List<Review> result = reviewSearchRepository.findReviewByCreatedDate();

        //then
        assertAll(
                () -> assertThat(result).hasSize(1),
                () -> assertThat(result.get(0)).isEqualTo(review),
                () -> assertThat(result.get(0).getUser()).isEqualTo(user),
                () -> assertThat(result.get(0).getHairStyle()).isEqualTo(hairStyle),
                () -> assertThat(result.get(0).getPhotos()).hasSize(A.getOriginalFilenames().size())
        );
    }
}
