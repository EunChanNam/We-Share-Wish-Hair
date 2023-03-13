package com.inq.wishhair.wesharewishhair.hairstyle.service;

import com.inq.wishhair.wesharewishhair.common.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.domain.hairstyle.HairStyle;
import com.inq.wishhair.wesharewishhair.domain.hairstyle.repository.HairStyleRepository;
import com.inq.wishhair.wesharewishhair.domain.hairstyle.service.HairStyleService;
import com.inq.wishhair.wesharewishhair.domain.hashtag.HashTag;
import com.inq.wishhair.wesharewishhair.domain.login.dto.UserSessionDto;
import com.inq.wishhair.wesharewishhair.domain.photo.entity.Photo;
import com.inq.wishhair.wesharewishhair.domain.user.User;
import com.inq.wishhair.wesharewishhair.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.fixture.UserFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.inq.wishhair.wesharewishhair.fixture.HairStyleFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional(readOnly = true)
@DisplayName("HairStyleServiceTest - SpringBootTest")
public class HairStyleServiceTest extends ServiceTest {

    @Autowired
    private HairStyleService hairStyleService;

    @Autowired
    private HairStyleRepository hairStyleRepository;

    @BeforeEach
    void init() {
        //given
        HairStyle A = HairStyleFixture.A.toEntity();
        hairStyleRepository.save(A);
    }

    @Test
    @DisplayName("Tag List 와 UserSessionDto 로 헤어스타일을 조회한다.")
    void test1() {
        //given
        User user = UserFixture.B.toEntity();
        //when
        List<HairStyle> result = hairStyleService.findRecommendedHairStyle(A.getTags(), new UserSessionDto(user));

        //then
        Assertions.assertAll(
                () -> assertThat(result).isNotEmpty(),
                () -> assertThat(result.size()).isEqualTo(1),
                () -> assertThat(result.get(0).getSex()).isEqualTo(user.getSex()),
                () -> assertThat(result.get(0).getSex()).isEqualTo(A.getSex()),
                () -> assertThat(result.get(0).getName()).isEqualTo(A.getName()),
                () -> assertThat(result.get(0).getHashTags().stream()
                        .map(HashTag::getTag).toList())
                        .containsAll(A.getTags()),
                () -> assertThat(result.get(0).getPhotos().stream()
                        .map(Photo::getOriginalFilename).toList())
                        .containsAll(A.getOriginalFilenames())
        );
    }
}
