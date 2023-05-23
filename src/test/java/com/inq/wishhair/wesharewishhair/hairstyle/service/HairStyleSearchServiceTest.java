package com.inq.wishhair.wesharewishhair.hairstyle.service;

import com.inq.wishhair.wesharewishhair.global.dto.response.PagedResponse;
import com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.global.dto.response.ResponseWrapper;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.HashTag;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.Tag;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.wishhair.WishHair;
import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.HairStyleResponse;
import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.HairStyleSimpleResponse;
import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.HashTagResponse;
import com.inq.wishhair.wesharewishhair.user.domain.FaceShape;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture.*;
import static com.inq.wishhair.wesharewishhair.global.utils.PageableGenerator.getDefaultPageable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("HairStyleServiceTest - SpringBootTest")
public class HairStyleSearchServiceTest extends ServiceTest {

    @Autowired
    private HairStyleSearchService hairStyleSearchService;

    private final HairStyle[] hairStyles = new HairStyle[values().length];
    private User user;

    @BeforeEach
    void init() {
        //given
        user = userRepository.save(UserFixture.B.toEntity());

        HairStyleFixture[] fixtures = values();
        for (int i = 0; i < values().length; i++) {
            hairStyles[i] = hairStyleRepository.save(fixtures[i].toEntity());
        }
    }

    @Nested
    @DisplayName("헤어스타일 추천 로직")
    class findRecommendedHairStyle {
        @Test
        @DisplayName("사용자의 얼굴형 태그가 포함되지 않은 헤어스타일은 조회되지 않는다")
        void test1() {
            //given
            List<Tag> tags = new ArrayList<>(A.getTags());
            user.updateFaceShape(new FaceShape(Tag.SQUARE));

            //when
            ResponseWrapper<HairStyleResponse> result =
                    hairStyleSearchService.recommendHair(tags, user.getId());

            //then
            List<HairStyleResponse> actual = result.getResult();
            assertHairStyleResponseMatch(actual, List.of(0));
        }

        @Test
        @DisplayName("사용자가 얼굴형 태그가 없는 사용자면 403 예외를 던진다")
        void test2() {
            //given
            List<Tag> tags = new ArrayList<>(List.of(Tag.PERM));
            ErrorCode expectedError = ErrorCode.USER_NO_FACE_SHAPE_TAG;

            //when, then
            assertThatThrownBy(() -> hairStyleSearchService.recommendHair(tags, user.getId()))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(expectedError.getMessage());
        }

        @Test
        @DisplayName("조회된 헤어스타일은 일치하는 해시태그 수, 이름으로 정렬된다")
        void test3() {
            //given
            List<Tag> tags = new ArrayList<>(List.of(Tag.PERM, Tag.LIGHT));
            user.updateFaceShape(new FaceShape(Tag.OBLONG));

            //when
            ResponseWrapper<HairStyleResponse> result =
                    hairStyleSearchService.recommendHair(tags, user.getId());

            //then
            List<HairStyleResponse> actual = result.getResult();
            assertHairStyleResponseMatch(actual, List.of(4, 2, 3));
        }
    }

    @Nested
    @DisplayName("사용자 얼굴형 기반 헤어추천 서비스 로직")
    class findHairStyleByFaceShape {
        @Test
        @DisplayName("얼굴형 태그가 저장돼 있는 유저라면 얼굴형 태그 기반으로 헤어가 찜수, 이름으로 정렬되어 조회된다")
        void test5() {
            //given
            user.updateFaceShape(new FaceShape(Tag.OBLONG));
            wishHairStyles(List.of(2, 2, 4));

            //when
            ResponseWrapper<HairStyleResponse> result = hairStyleSearchService.recommendHairByFaceShape(user.getId());

            //then
            List<HairStyleResponse> actual = result.getResult();
            assertHairStyleResponseMatch(actual, List.of(2, 4, 3));
        }

        @Test
        @DisplayName("얼굴형 태그가 저장돼 있지 않은 유저라면 태그 없이 헤어가 찜수, 이름으로 정렬되어 조회된다")
        void test6() {
            //given
            wishHairStyles(List.of(2, 2, 0));

            //when
            ResponseWrapper<HairStyleResponse> result =
                    hairStyleSearchService.recommendHairByFaceShape(user.getId());

            //then
            List<HairStyleResponse> actual = result.getResult();
            assertHairStyleResponseMatch(actual, List.of(2, 0, 3, 4));
        }
    }

    @Nested
    @DisplayName("사용자가 찜한 헤어스타일 조회 서비스 로직")
    class findWishHairStyles {
        @Test
        @DisplayName("찜한 헤어스타일이 없으면 아무것도 조회되지 않는다")
        void noResult() {
            //when
            PagedResponse<HairStyleResponse> result = hairStyleSearchService.findWishHairStyles(user.getId(), getDefaultPageable());

            //then
            assertThat(result.getPaging().hasNext()).isFalse();
            assertThat(result.getResult()).isEmpty();
        }

        @Test
        @DisplayName("찜한 헤어스타일을 최신 순으로 조회한다")
        void success() {
            //given
            wishHairStyles(List.of(2, 0, 3));

            //when
            PagedResponse<HairStyleResponse> result = hairStyleSearchService.findWishHairStyles(user.getId(), getDefaultPageable());

            //then
            assertThat(result.getPaging().hasNext()).isFalse();
            assertHairStyleResponseMatch(result.getResult(), List.of(3, 0, 2));
        }
    }

    @Test
    @DisplayName("전체 헤어스타일을 이름으로 정렬해서 조회한다")
    void findAllHairStyle() {
        ResponseWrapper<HairStyleSimpleResponse> result = hairStyleSearchService.findAllHairStyle();

        //then
        List<HairStyleSimpleResponse> actual = result.getResult();
        assertHairStyleSimpleResponseMatch(actual, List.of(0, 1, 2, 3, 4));
    }

    private void wishHairStyles(List<Integer> indexes) {
        for (int index : indexes) {
            Long hairStyleId = hairStyles[index].getId();
            wishHairRepository.save(WishHair.createWishHair(user.getId(), hairStyleId));
        }
    }

    private void assertHairStyleResponseMatch(List<HairStyleResponse> actualList, List<Integer> indexes) {
        assertThat(actualList).hasSize(indexes.size());
        for (int i = 0; i < actualList.size(); i++) {
            int index = indexes.get(i);
            HairStyleResponse actual = actualList.get(i);
            HairStyle expected = hairStyles[index];
            assertAll(
                    () -> assertThat(actual.getHairStyleId()).isEqualTo(expected.getId()),
                    () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
                    () -> {
                        List<String> resultTags = actual.getHashTags().stream().map(HashTagResponse::getTag).toList();
                        List<String> actualTags = expected.getHashTags().stream().map(HashTag::getTag).map(Tag::getDescription).toList();
                        assertThat(resultTags).containsAll(actualTags);
                    },
                    () -> assertThat(actual.getPhotos()).hasSize(expected.getPhotos().size())
            );
        }
    }

    private void assertHairStyleSimpleResponseMatch(List<HairStyleSimpleResponse> actualList, List<Integer> indexes) {
        assertThat(actualList).hasSize(indexes.size());
        for (int i = 0; i < actualList.size(); i++) {
            int index = indexes.get(i);
            HairStyleSimpleResponse actual = actualList.get(i);
            HairStyle expected = hairStyles[index];

            assertAll(
                    () -> assertThat(actual.getHairStyleId()).isEqualTo(expected.getId()),
                    () -> assertThat(actual.getHairStyleName()).isEqualTo(expected.getName())
            );
        }
    }
}
