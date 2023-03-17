package com.inq.wishhair.wesharewishhair.hairstyle.service;

import com.inq.wishhair.wesharewishhair.common.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.common.utils.UserSessionDtoUtils;
import com.inq.wishhair.wesharewishhair.domain.hairstyle.HairStyle;
import com.inq.wishhair.wesharewishhair.domain.hairstyle.repository.HairStyleRepository;
import com.inq.wishhair.wesharewishhair.domain.hairstyle.service.HairStyleService;
import com.inq.wishhair.wesharewishhair.domain.login.dto.UserSessionDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.inq.wishhair.wesharewishhair.fixture.HairStyleFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("HairStyleServiceTest - SpringBootTest")
public class HairStyleServiceTest extends ServiceTest {

    @Autowired
    private HairStyleService hairStyleService;

    private HairStyle a;
    private HairStyle b;
    private HairStyle c;
    private HairStyle d;

    @BeforeEach
    void init() {
        //given
        a = A.toEntity();
        b = B.toEntity();
        c = C.toEntity();
        d = D.toEntity();
        hairStyleRepository.save(a);
        hairStyleRepository.save(b);
        hairStyleRepository.save(c);
        hairStyleRepository.save(d);
    }

    @Test
    @DisplayName("일치하는 해시태그 갯수와 헤어스타일의 이름으로 정렬하여 헤어스타일을 조회한다.")
    void test1() {
        //given
        Pageable pageable = PageRequest.of(0, 4);
        UserSessionDto sessionDto = UserSessionDtoUtils.getBSessionDto();
        //when
        List<HairStyle> result =
                hairStyleService.findRecommendedHairStyle(A.getTags(), sessionDto, pageable);

        //then
        Assertions.assertAll(
                () -> assertThat(result).isNotEmpty(),
                () -> assertThat(result.size()).isEqualTo(3),
                () -> assertThat(result).containsExactly(a, c, d)
        );
    }
}
