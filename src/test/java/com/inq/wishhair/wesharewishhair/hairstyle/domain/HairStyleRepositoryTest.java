package com.inq.wishhair.wesharewishhair.hairstyle.domain;

import com.inq.wishhair.wesharewishhair.common.base.RepositoryTest;
import com.inq.wishhair.wesharewishhair.domain.hairstyle.HairStyle;
import com.inq.wishhair.wesharewishhair.domain.hairstyle.repository.HairStyleRepository;
import com.inq.wishhair.wesharewishhair.domain.hashtag.HashTag;
import com.inq.wishhair.wesharewishhair.domain.hashtag.enums.Tag;
import com.inq.wishhair.wesharewishhair.domain.photo.entity.Photo;
import com.inq.wishhair.wesharewishhair.domain.user.enums.Sex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.inq.wishhair.wesharewishhair.fixture.HairStyleFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class HairStyleRepositoryTest extends RepositoryTest {

    @Autowired
    private HairStyleRepository hairStyleRepository;

    @BeforeEach
    void init() {
        //given
        HairStyle a = A.toEntity();
        HairStyle b = B.toEntity();
        HairStyle c = C.toEntity();
        HairStyle d = D.toEntity();
        hairStyleRepository.save(a);
        hairStyleRepository.save(b);
        hairStyleRepository.save(c);
        hairStyleRepository.save(d);
    }

    //todo 이런거 조건 여러개 따지면서 테스트하는 부분은 레포에서 해야되는지 서비스에서 해야되는지
    @Test
    @DisplayName("태그와 유저의 성별을 통해서 헤어스타일을 조회한다.")
    void test1() {
        //given
        List<Tag> tags = B.getTags();
        Sex sex = B.getSex();
        Pageable pageable = PageRequest.of(0, 4);

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


}
