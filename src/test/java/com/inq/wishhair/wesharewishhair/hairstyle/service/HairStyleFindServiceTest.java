package com.inq.wishhair.wesharewishhair.hairstyle.service;

import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("HairStyleFindTest - SpringBootTest")
public class HairStyleFindServiceTest extends ServiceTest {

    @Autowired
    private HairStyleFindService hairStyleFindService;

    @Test
    @DisplayName("아이디로 헤어스타일을 조회한다")
    void findById() {
        //given
        HairStyle hairStyle = hairStyleRepository.save(HairStyleFixture.A.toEntity());

        //when
        HairStyle result = hairStyleFindService.findById(hairStyle.getId());

        //then
        assertThat(hairStyle).isEqualTo(result);
    }
}
