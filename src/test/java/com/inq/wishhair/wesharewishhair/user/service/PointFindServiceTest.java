package com.inq.wishhair.wesharewishhair.user.service;

import com.inq.wishhair.wesharewishhair.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.global.utils.PageableUtils;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.service.dto.response.PointResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import static com.inq.wishhair.wesharewishhair.fixture.UserFixture.A;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PointFindServiceTest - SpringBootTest")
public class PointFindServiceTest extends ServiceTest {

    @Autowired
    private PointFindService pointFindService;

    @Autowired
    private PointService pointService;

    private final User user = A.toEntity();

    @BeforeEach
    void setUp() {
        userRepository.save(user);

        //given
        pointService.chargePoint(1000, user.getId());
        pointService.chargePoint(1000, user.getId());
        pointService.chargePoint(1000, user.getId());
    }

    @Test
    @DisplayName("사용자의 포인트 내역을 조회한다")
    void test1() {
        //given
        Pageable pageable = PageableUtils.generateSimplePageable(3);

        //when
        Slice<PointResponse> result = pointFindService.findPointHistories(user.getId(), pageable);

        //then
        assertAll(
                () -> assertThat(result).isNotEmpty(),
                () -> assertThat(result).hasSize(3),
                () -> assertThat(result.getContent().stream().map(PointResponse::getPoint))
                        .contains(1000, 2000, 3000)
        );

    }
}
