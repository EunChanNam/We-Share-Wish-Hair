package com.inq.wishhair.wesharewishhair.user.domain;

import com.inq.wishhair.wesharewishhair.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.global.base.RepositoryTest;
import com.inq.wishhair.wesharewishhair.global.utils.PageableUtils;
import com.inq.wishhair.wesharewishhair.user.domain.point.PointSearchRepository;
import com.inq.wishhair.wesharewishhair.user.domain.point.PointHistory;
import com.inq.wishhair.wesharewishhair.user.domain.point.PointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import static com.inq.wishhair.wesharewishhair.user.domain.point.PointType.CHARGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("PointFindRepositoryTest - DataJpaTest")
public class PointSearchRepositoryTest extends RepositoryTest {

    @Autowired
    private PointSearchRepository pointSearchRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PointRepository pointRepository;

    private final User user = UserFixture.A.toEntity();

    @BeforeEach
    void setUp() {
        userRepository.save(user);

        //given
        pointRepository.save(PointHistory.createPointHistory(user, CHARGE, 1000, 1000));
        pointRepository.save(PointHistory.createPointHistory(user, CHARGE, 1000, 2000));
    }

    @Test
    @DisplayName("유저의 아이디와 페이징 정보로 포인트 내역을 조회한다")
    void test1() {
        //given
        Pageable pageable1 = PageableUtils.generateSimplePageable(1);
        Pageable pageable2 = PageableUtils.generateSimplePageable(2);

        //when
        Slice<PointHistory> result1 = pointSearchRepository.findByUserIdOrderByNew(user.getId(), pageable1);
        Slice<PointHistory> result2 = pointSearchRepository.findByUserIdOrderByNew(user.getId(), pageable2);

        //then
        assertAll(
                () -> assertThat(result1).isNotEmpty(),
                () -> assertThat(result1.getContent()).hasSize(1),
                () -> assertThat(result2).isNotEmpty(),
                () -> assertThat(result2.getContent()).hasSize(2)
        );

    }
}
