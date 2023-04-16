package com.inq.wishhair.wesharewishhair.user.service;

import com.inq.wishhair.wesharewishhair.global.fixture.PointFixture;
import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.global.dto.response.PagedResponse;
import com.inq.wishhair.wesharewishhair.global.utils.DefaultPageableUtils;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.domain.point.PointHistory;
import com.inq.wishhair.wesharewishhair.user.service.dto.response.PointResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;

import static com.inq.wishhair.wesharewishhair.global.fixture.PointFixture.values;
import static com.inq.wishhair.wesharewishhair.global.fixture.UserFixture.A;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("PointFindServiceTest - SpringBootTest")
public class PointSearchServiceTest extends ServiceTest {

    @Autowired
    private PointSearchService pointSearchService;

    private User user;
    private final int length = values().length;
    private final PointHistory[] pointHistories = new PointHistory[length];

    @BeforeEach
    void setUp() {
        //given
        user = userRepository.save(A.toEntity());

        PointFixture[] fixtures = values();
        for (int i = 0; i < length; i++) {
            PointFixture fixture = fixtures[i];
            pointHistories[i] = PointHistory.createPointHistory(
                    user,
                    fixture.getPointType(),
                    fixture.getDealAmount(),
                    fixture.getPoint()
            );
        }
    }

    @Test
    @DisplayName("사용자의 포인트 내역을 최신 날짜 순으로 정렬하여 조회한다")
    void test1() {
        //given
        savePointHistories(List.of(1, 3, 4), List.of(now().minusMonths(1), now().minusMinutes(1), now().minusDays(1)));

        //when
        PagedResponse<PointResponse> result = pointSearchService.findPointHistories(user.getId());

        //then
        assertThat(result.getPaging().hasNext()).isFalse();
        assertPointResponseMatch(result.getResult(),
                List.of(pointHistories[3], pointHistories[4], pointHistories[1]));
    }

    private void assertPointResponseMatch(List<PointResponse> responses, List<PointHistory> expectedList) {
        assertThat(responses).hasSize(expectedList.size());

        for (int index = 0; index < responses.size(); index++) {
            PointResponse actual = responses.get(index);
            PointHistory expected = expectedList.get(index);

            assertAll(
                    () -> assertThat(actual.getPoint()).isEqualTo(expected.getPoint()),
                    () -> assertThat(actual.getPointType()).isEqualTo(expected.getPointType().getDescription()),
                    () -> assertThat(actual.getDealAmount()).isEqualTo(expected.getDealAmount()),
                    () -> assertThat(actual.getDealDate()).isEqualTo(expected.getCreatedDate())
            );
        }
    }

    private void savePointHistories(List<Integer> indexes, List<LocalDateTime> times) {
        for (int i = 0; i < indexes.size(); i++) {
            int index = indexes.get(i);
            PointHistory pointHistory = pointHistories[index];
            ReflectionTestUtils.setField(pointHistory, "createdDate", times.get(i));

            pointRepository.save(pointHistory);
        }
    }
}
