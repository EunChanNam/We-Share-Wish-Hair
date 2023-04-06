package com.inq.wishhair.wesharewishhair.user.service;

import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.global.dto.response.PagedResponse;
import com.inq.wishhair.wesharewishhair.global.utils.PageableUtils;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.service.dto.response.PointResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

import static com.inq.wishhair.wesharewishhair.fixture.UserFixture.A;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PointFindServiceTest - SpringBootTest")
public class PointSearchServiceTest extends ServiceTest {
    //todo 검증 코드 리팩토링
    @Autowired
    private PointSearchService pointSearchService;

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
        PagedResponse<PointResponse> result = pointSearchService.findPointHistories(user.getId(), pageable);

        //then
        List<PointResponse> actual = result.getResult();
        assertAll(
                () -> assertThat(actual).isNotEmpty(),
                () -> assertThat(actual).hasSize(3),
                () -> assertThat(actual.stream().map(PointResponse::getPoint))
                        .contains(1000, 2000, 3000)
        );

    }
}
