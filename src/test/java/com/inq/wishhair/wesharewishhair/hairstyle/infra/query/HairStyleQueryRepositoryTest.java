package com.inq.wishhair.wesharewishhair.hairstyle.infra.query;

import com.inq.wishhair.wesharewishhair.global.base.RepositoryTest;
import com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.enums.Tag;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.wishhair.WishHair;
import com.inq.wishhair.wesharewishhair.hairstyle.service.utils.HairRecommendConditionUtils;
import com.inq.wishhair.wesharewishhair.hairstyle.utils.HairRecommendCondition;
import com.inq.wishhair.wesharewishhair.user.domain.FaceShape;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.enums.Sex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.ArrayList;
import java.util.List;

import static com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture.*;
import static com.inq.wishhair.wesharewishhair.global.utils.PageableUtils.getDefaultPageable;
import static com.inq.wishhair.wesharewishhair.hairstyle.service.utils.HairRecommendConditionUtils.*;
import static com.inq.wishhair.wesharewishhair.hairstyle.service.utils.HairRecommendConditionUtils.mainRecommend;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class HairStyleQueryRepositoryTest extends RepositoryTest {

    private final HairStyle[] hairStyles = new HairStyle[values().length];

    @BeforeEach
    void init() {
        //given
        HairStyleFixture[] fixtures = values();
        for (int i = 0; i < values().length; i++) {
            hairStyles[i] = hairStyleRepository.save(fixtures[i].toEntity());
        }
    }

    @Nested
    @DisplayName("헤어스타일 추천 쿼리")
    class findByHashTags {
        @Test
        @DisplayName("태그와 유저의 성별을 통해서 헤어스타일을 조회한다.")
        void test1() {
            //given
            HairRecommendCondition condition = mainRecommend(A.getTags(), UserFixture.A.toEntity());

            //when
            List<HairStyle> result = hairStyleRepository.findByRecommend(condition, getDefaultPageable());

            //then
            assertHairStylesMatch(result, List.of(1));
        }

        @Test
        @DisplayName("성별이 맞고 태그가 하나라도 포함되면 해당 헤어스타일은 조회된다")
        void test2() {
            //given
            HairRecommendCondition condition = mainRecommend(List.of(Tag.PERM), UserFixture.B.toEntity());

            //when
            List<HairStyle> result = hairStyleRepository.findByRecommend(condition, getDefaultPageable());

            //then
            assertHairStylesMatch(result, List.of(2, 0, 4, 3));
        }

        @Test
        @DisplayName("조회된 헤어스타일은 해시태그의 개수, 찜수, 이름으로 정렬된다")
        void test3() {
            //given
            HairRecommendCondition condition = mainRecommend(D.getTags(), UserFixture.B.toEntity());

            //when
            List<HairStyle> result = hairStyleRepository.findByRecommend(condition, getDefaultPageable());

            //then
            assertHairStylesMatch(result, List.of(3, 4, 2, 0));
        }
    }

    @Nested
    @DisplayName("사용자 얼굴형 기반 맞춤 헤어 추천 쿼리")
    class findByFaceShapeTag {
        @Test
        @DisplayName("얼굴형 태그로 헤어를 검색하고, 찜 수와 이름으로 정렬한다")
        void test4() {
            //given
            User user = UserFixture.B.toEntity();
            user.updateFaceShape(new FaceShape(Tag.OBLONG));
            HairRecommendCondition condition = subRecommend(user);

            //when
            List<HairStyle> result = hairStyleRepository.findByRecommend(condition, getDefaultPageable());

            //then
            assertHairStylesMatch(result, List.of(2, 4, 3));
        }

        @Test
        @DisplayName("얼굴형 태그 없이 검색 후 찜 수와 이름으로 정렬한다")
        void test5() {
            //given
            User user = UserFixture.B.toEntity();
            HairRecommendCondition condition = subRecommend(user);

            //when
            List<HairStyle> result = hairStyleRepository.findByRecommend(condition, getDefaultPageable());

            //then
            assertHairStylesMatch(result, List.of(2, 0, 4, 3));
        }
    }

    @Nested
    @DisplayName("사용자가 찜한 헤어스타일을 조회한다")
    class findByWish {
        @Test
        @DisplayName("찜한 헤어스타일이 없어 아무것도 조회되지 않는다")
        void noResult() {
            //when
            Slice<HairStyle> result = hairStyleRepository.findByWish(1L, getDefaultPageable());

            //then
            assertThat(result.hasNext()).isFalse();
            assertThat(result.getContent()).isEmpty();
        }

        @Test
        @DisplayName("찜한 헤어스타일을 생성된 순서로 조회한다")
        void success() {
            //given
            wishHairStyles(List.of(3, 0, 1));

            //when
            Slice<HairStyle> result = hairStyleRepository.findByWish(1L, getDefaultPageable());

            //then
            assertThat(result.hasNext()).isFalse();
            assertHairStylesMatch(result.getContent(), List.of(1, 0, 3));
        }
    }

    private void wishHairStyles(List<Integer> indexes) {
        for (int index : indexes) {
            Long hairStyleId = hairStyles[index].getId();
            wishHairRepository.save(WishHair.createWishHair(1L, hairStyleId));
        }
    }

    private void assertHairStylesMatch(List<HairStyle> results, List<Integer> indexes) {
        assertThat(results).hasSize(indexes.size());
        for (int i = 0; i < results.size(); i++) {
            int index = indexes.get(i);
            HairStyle result = results.get(i);
            HairStyle actual = hairStyles[index];

            assertThat(result).isEqualTo(actual);
        }
    }
}
