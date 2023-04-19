package com.inq.wishhair.wesharewishhair.hairstyle.domain;

import com.inq.wishhair.wesharewishhair.global.base.RepositoryTest;
import com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.HashTag;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.enums.Tag;
import com.inq.wishhair.wesharewishhair.photo.domain.Photo;
import com.inq.wishhair.wesharewishhair.user.enums.Sex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture.*;
import static com.inq.wishhair.wesharewishhair.global.utils.PageableUtils.getDefaultPageable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class HairStyleSearchRepositoryTest extends RepositoryTest {

    @Autowired
    private HairStyleSearchRepository hairStyleSearchRepository;

    private final HairStyle[] hairStyles = new HairStyle[values().length];

    @BeforeEach
    void init() {
        //given
        HairStyleFixture[] fixtures = values();
        for (int i = 0; i < values().length; i++) {
            hairStyles[i] = hairStyleSearchRepository.save(fixtures[i].toEntity());
        }
    }

    @Nested
    @DisplayName("헤어스타일 추천 쿼리")
    class findByHashTags {
        @Test
        @DisplayName("태그와 유저의 성별을 통해서 헤어스타일을 조회한다.")
        void test1() {
            //given
            List<Tag> tags = B.getTags();
            Sex sex = B.getSex();

            //when
            List<HairStyle> result = hairStyleSearchRepository.findByHashTags(tags, sex, getDefaultPageable());

            //then
            assertHairStylesMatch(result, List.of(1));
        }

        @Test
        @DisplayName("성별이 맞고 태그가 하나라도 포함되면 해당 헤어스타일은 조회된다")
        void test2() {
            //given
            List<Tag> tags = new ArrayList<>(List.of(Tag.PERM));
            Pageable pageable = getDefaultPageable();

            //when
            List<HairStyle> result = hairStyleSearchRepository.findByHashTags(tags, A.getSex(), pageable);

            //then
            assertHairStylesMatch(result, List.of(2, 0, 4, 3));
        }

        @Test
        @DisplayName("조회된 헤어스타일은 해시태그의 개수, 찜수, 이름으로 정렬된다")
        void test3() {
            //given
            List<Tag> tags = D.getTags();

            //when
            List<HairStyle> result = hairStyleSearchRepository.findByHashTags(tags, D.getSex(), getDefaultPageable());

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
            Tag faceShapeTag = Tag.OBLONG;
            Pageable pageable = getDefaultPageable();

            E.getTags().forEach(System.out::println);

            //when
            List<HairStyle> result = hairStyleSearchRepository.findByFaceShapeTag(faceShapeTag, A.getSex(), pageable);

            result.forEach(a -> System.out.println(a.getName()));

            //then
            assertHairStylesMatch(result, List.of(2, 4, 3));
        }

        @Test
        @DisplayName("얼굴형 태그 없이 검색 후 찜 수와 이름으로 정렬한다")
        void test5() {
            //given
            Pageable pageable = getDefaultPageable();

            //when
            List<HairStyle> result = hairStyleSearchRepository.findByNoFaceShapeTag(A.getSex(), pageable);

            //then
            assertHairStylesMatch(result, List.of(2, 0, 4, 3));
        }
    }

    private void assertHairStylesMatch(List<HairStyle> results, List<Integer> indexes) {
        assertThat(results).hasSize(indexes.size());
        for (int i = 0; i < results.size(); i++) {
            int index = indexes.get(i);
            HairStyle result = results.get(i);
            HairStyle actual = hairStyles[index];
            assertAll(
                    () -> assertThat(result.getSex()).isEqualTo(actual.getSex()),
                    () -> assertThat(result.getName()).isEqualTo(actual.getName()),
                    () -> {
                        List<Tag> resultTags = result.getHashTags().stream().map(HashTag::getTag).toList();
                        List<Tag> actualTags = actual.getHashTags().stream().map(HashTag::getTag).toList();
                        assertThat(resultTags).containsAll(actualTags);
                    },
                    () -> {
                        List<String> resultOriginalFilenames = result.getPhotos().stream()
                                .map(Photo::getOriginalFilename).toList();
                        List<String> actualOriginalFilenames = actual.getPhotos().stream()
                                .map(Photo::getOriginalFilename).toList();
                        assertThat(resultOriginalFilenames).containsAll(actualOriginalFilenames);
                    },
                    () -> assertThat(result.getWishListCount()).isEqualTo(actual.getWishListCount())
            );
        }
    }
}
