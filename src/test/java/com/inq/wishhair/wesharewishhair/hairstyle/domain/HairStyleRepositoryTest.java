package com.inq.wishhair.wesharewishhair.hairstyle.domain;

import com.inq.wishhair.wesharewishhair.global.base.RepositoryTest;
import com.inq.wishhair.wesharewishhair.global.utils.PageableUtils;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.HashTag;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.enums.Tag;
import com.inq.wishhair.wesharewishhair.photo.entity.Photo;
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

public class HairStyleRepositoryTest extends RepositoryTest {

    @Autowired
    private HairStyleRepository hairStyleRepository;

    private HairStyle a;
    private HairStyle c;
    private HairStyle d;
    private HairStyle e;

    @Nested
    @DisplayName("헤어스타일 추천 쿼리")
    class findByHashTags {

        @BeforeEach
        void init() {
            //given
            a = A.toEntity();
            c = C.toEntity();
            d = D.toEntity();
            e = E.toEntity();
            hairStyleRepository.save(a);
            hairStyleRepository.save(B.toEntity());
            hairStyleRepository.save(c);
            hairStyleRepository.save(d);
            hairStyleRepository.save(e);
        }

        @Test
        @DisplayName("태그와 유저의 성별을 통해서 헤어스타일을 조회한다.")
        void test1() {
            //given
            List<Tag> tags = B.getTags();
            Sex sex = B.getSex();
            Pageable pageable = PageableUtils.getDefaultPageable();

            //when
            List<HairStyle> result = hairStyleRepository.findByHashTags(tags, sex, pageable);

            //then
            assertAll(
                    () -> assertThat(result.size()).isEqualTo(1),
                    () -> assertThat(result.get(0).getSex()).isEqualTo(sex),
                    () -> assertThat(result.get(0).getName()).isEqualTo(B.getName()),
                    () -> assertThat(result.get(0).getHashTags().stream()
                            .map(HashTag::getTag).toList())
                            .containsAll(tags),
                    () -> assertThat(result.get(0).getPhotos().stream()
                            .map(Photo::getOriginalFilename).toList())
                            .containsAll(B.getOriginalFilenames())
            );
        }

        @Test
        @DisplayName("성별이 맞고 태그가 하나라도 포함되면 해당 헤어스타일은 조회된다")
        void test2() {
            //given
            List<Tag> tags = new ArrayList<>(List.of(Tag.PERM));
            Pageable pageable = PageableUtils.getDefaultPageable();

            //when
            List<HairStyle> result = hairStyleRepository.findByHashTags(tags, A.getSex(), pageable);

            //then
            assertAll(
                    () -> assertThat(result.size()).isEqualTo(4),
                    () -> assertThat(result).contains(a, c, d, e)
            );
        }

        @Test
        @DisplayName("조회된 헤어스타일은 해시태그의 개수와 이름으로 정렬된다")
        void test3() {
            //given
            List<Tag> tags = A.getTags();
            Pageable pageable = PageableUtils.getDefaultPageable();

            //when
            List<HairStyle> result = hairStyleRepository.findByHashTags(tags, A.getSex(), pageable);

            //then
            assertAll(
                    () -> assertThat(result.size()).isEqualTo(4),
                    () -> assertThat(result).containsExactly(a, c, d, e)
            );
        }

        @Test
        @DisplayName("해시태그가 하나도 포함되지 않으면 조회되지 않는다")
        void test4() {
            //given
            List<Tag> tags = new ArrayList<>();
            Pageable pageable = PageableUtils.getDefaultPageable();

            //when
            List<HairStyle> result = hairStyleRepository.findByHashTags(tags, B.getSex(), pageable);

            assertThat(result).isEmpty();
        }
    }
}
