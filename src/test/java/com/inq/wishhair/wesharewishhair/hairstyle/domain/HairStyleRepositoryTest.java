package com.inq.wishhair.wesharewishhair.hairstyle.domain;

import com.inq.wishhair.wesharewishhair.common.base.RepositoryTest;
import com.inq.wishhair.wesharewishhair.domain.hairstyle.HairStyle;
import com.inq.wishhair.wesharewishhair.domain.hairstyle.repository.HairStyleRepository;
import com.inq.wishhair.wesharewishhair.domain.hashtag.enums.Tag;
import com.inq.wishhair.wesharewishhair.domain.user.enums.Sex;
import com.inq.wishhair.wesharewishhair.fixture.HairStyleFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static com.inq.wishhair.wesharewishhair.fixture.HairStyleFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

public class HairStyleRepositoryTest extends RepositoryTest {

    @Autowired
    private HairStyleRepository hairStyleRepository;

    @BeforeEach
    void init() {
        //given
        HairStyle A = HairStyleFixture.A.toEntity();
        HairStyle B = HairStyleFixture.B.toEntity();
        hairStyleRepository.save(A);
        hairStyleRepository.save(B);
    }

    @Test
    @DisplayName("가진 해시태그보다 많은 해시태그로 조회 시 헤어스타일이 조회되지 않는다")
    void overHashTagSizeTest() {
        //given
        List<Tag> tags = A.getTags();
        tags.add(Tag.BANGS);

        //when
        List<HairStyle> result = hairStyleRepository.findByHashTags(tags, tags.size(), A.getSex());

        //then
        assertThat(result).isEmpty();
    }


}
