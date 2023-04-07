package com.inq.wishhair.wesharewishhair.user.service;

import com.inq.wishhair.wesharewishhair.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.global.utils.DefaultPageableUtils;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.service.dto.response.MyPageResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("User-MyPageServiceTest - SpringBootTest")
public class MyPageServiceTest extends ServiceTest {

    @Autowired
    private MyPageService myPageService;

    private User user;

    @BeforeEach
    void setUp() {
        //given
        user = userRepository.save(UserFixture.B.toEntity());
    }

    @Test
    @DisplayName("사용자의 마이페이지 정보를 조회한다")
    void getMyPageInfo() {
        //given
        Pageable pageable = DefaultPageableUtils.getDateDescPageable(3);

        //when
        MyPageResponse result = myPageService.getMyPageInfo(user.getId(), pageable);

        //then
        assertAll(
                () -> assertThat(result.getNickname()).isEqualTo(user.getNicknameValue()),
                () -> assertThat(result.getPoint()).isEqualTo(user.getAvailablePoint()),
                () -> assertThat(result.getSex()).isEqualTo(user.getSex()),
                () -> assertThat(result.getReviews()).isEmpty()
        );
    }
}
