package com.inq.wishhair.wesharewishhair.hairstyle.domain;

import com.inq.wishhair.wesharewishhair.global.base.RepositoryTest;
import com.inq.wishhair.wesharewishhair.global.utils.PageableUtils;
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

import static com.inq.wishhair.wesharewishhair.fixture.HairStyleFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class HairStyleSearchRepositoryTest extends RepositoryTest {

    @Autowired
    private HairStyleSearchRepository hairStyleSearchRepository;

    private HairStyle a;
    private HairStyle c;
    private HairStyle d;
    private HairStyle e;

    @BeforeEach
    void init() {
        //given
        a = A.toEntity();
        c = C.toEntity();
        d = D.toEntity();
        e = E.toEntity();
        hairStyleSearchRepository.save(a);
        hairStyleSearchRepository.save(B.toEntity());
        hairStyleSearchRepository.save(c);
        hairStyleSearchRepository.save(d);
        hairStyleSearchRepository.save(e);
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
            Pageable pageable = PageableUtils.getDefaultPageable();

            //when
            List<HairStyle> result = hairStyleSearchRepository.findByHashTags(tags, sex, pageable);

            //then
            assertAll(
                    () -> assertThat(result).hasSize(1),
                    () -> assertThat(result.get(0).getSex()).isEqualTo(sex),
                    () -> assertThat(result.get(0).getName()).isEqualTo(B.getName()),
                    () -> assertThat(result.get(0).getHashTags().stream()
                            .map(HashTag::getTag).toList())
                            .containsAll(tags),
                    () -> assertThat(result.get(0).getPhotos().stream()
                            .map(Photo::getOriginalFilename).toList())
                            .containsAll(B.getOriginalFilenames()),
                    () -> assertThat(result.get(0).getWishListCount()).isEqualTo(B.getWishListCount())
            );
        }

        @Test
        @DisplayName("성별이 맞고 태그가 하나라도 포함되면 해당 헤어스타일은 조회된다")
        void test2() {
            //given
            List<Tag> tags = new ArrayList<>(List.of(Tag.PERM));
            Pageable pageable = PageableUtils.getDefaultPageable();

            //when
            List<HairStyle> result = hairStyleSearchRepository.findByHashTags(tags, A.getSex(), pageable);

            //then
            assertAll(
                    () -> assertThat(result).hasSize(4),
                    () -> assertThat(result).contains(a, c, d, e)
            );
        }

        @Test
        @DisplayName("조회된 헤어스타일은 해시태그의 개수, 찜수, 이름으로 정렬된다")
        void test3() {
            //given
            List<Tag> tags = A.getTags();
            Pageable pageable = PageableUtils.getDefaultPageable();

            //when
            List<HairStyle> result = hairStyleSearchRepository.findByHashTags(tags, A.getSex(), pageable);

            //then
            assertAll(
                    () -> assertThat(result).hasSize(4),
                    () -> assertThat(result).containsExactly(a, c, d, e)
            );
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
            Pageable pageable = PageableUtils.getDefaultPageable();

            //when
            List<HairStyle> result = hairStyleSearchRepository.findByFaceShapeTag(faceShapeTag, A.getSex(), pageable);

            //then
            assertAll(
                    () -> assertThat(result).hasSize(3),
                    () -> assertThat(result).containsExactly(c, e, d)
            );
        }

        @Test
        @DisplayName("얼굴형 태그 없이 검색 후 찜 수와 이름으로 정렬한다")
        void test5() {
            //given
            Pageable pageable = PageableUtils.getDefaultPageable();

            //when
            List<HairStyle> result = hairStyleSearchRepository.findByNoFaceShapeTag(A.getSex(), pageable);

            //then
            assertAll(
                    () -> assertThat(result).hasSize(4),
                    () -> assertThat(result).containsExactly(c, a, e, d)
            );
        }
    }
}
