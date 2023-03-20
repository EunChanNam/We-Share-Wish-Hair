package com.inq.wishhair.wesharewishhair.hairstyle.service;

import com.inq.wishhair.wesharewishhair.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.global.utils.PageableUtils;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.enums.Tag;
import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.HairStyleResponse;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static com.inq.wishhair.wesharewishhair.fixture.HairStyleFixture.*;
import static com.inq.wishhair.wesharewishhair.global.utils.PageableUtils.getDefaultPageable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("HairStyleServiceTest - SpringBootTest")
public class HairStyleServiceTest extends ServiceTest {

    @Autowired
    private HairStyleService hairStyleService;

    @BeforeEach
    void setUp() {
        //given
        hairStyleRepository.save(A.toEntity());
        hairStyleRepository.save(B.toEntity());
        hairStyleRepository.save(C.toEntity());
        hairStyleRepository.save(D.toEntity());
        hairStyleRepository.save(E.toEntity());
    }

    @Nested
    @DisplayName("헤어스타일 추천 로직")
    class findRecommendedHairStyle {
        @Test
        @DisplayName("성별이 다른 헤어스타일은 조회되지 않으며 해시태그 개수와 이름으로 정렬돼 조회된다")
        void test1() {
            //given
            List<Tag> tags = List.of(Tag.PERM);
            Long userId = userRepository.save(UserFixture.B.toEntity()).getId();

            //when
            List<HairStyleResponse> result =
                    hairStyleService.findRecommendedHairStyle(tags, userId, getDefaultPageable());

            //then
            assertAll(
                    () -> assertThat(result.size()).isEqualTo(4),
                    () -> assertThat(result.stream().map(HairStyleResponse::getName))
                            .containsExactly(A.getName(), C.getName(), D.getName(), E.getName())
            );

        }
    }
}
