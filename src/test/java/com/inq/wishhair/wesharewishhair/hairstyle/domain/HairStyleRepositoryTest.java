package com.inq.wishhair.wesharewishhair.hairstyle.domain;

import com.inq.wishhair.wesharewishhair.global.base.RepositoryTest;
import com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("")
public class HairStyleRepositoryTest extends RepositoryTest {

    private List<HairStyle> hairStyles;

    @BeforeEach
    void setUp() {
        //given
        HairStyleFixture[] values = HairStyleFixture.values();
        hairStyles = Arrays.stream(values).map(HairStyleFixture::toEntity).toList();
        hairStyleRepository.saveAll(hairStyles);
    }

    @Test
    @DisplayName("모든 헤어스타일을 이름순으로 정렬해서 조회한다")
    void findAllByOrderByName() {
        //when
        List<HairStyle> result = hairStyleRepository.findAllByOrderByName();

        //then
        assertThat(result).containsExactly(hairStyles.toArray(HairStyle[]::new));
    }
}
