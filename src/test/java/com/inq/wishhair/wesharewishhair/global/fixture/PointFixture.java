package com.inq.wishhair.wesharewishhair.global.fixture;

import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.domain.point.PointHistory;
import com.inq.wishhair.wesharewishhair.user.domain.point.PointType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.inq.wishhair.wesharewishhair.user.domain.point.PointType.CHARGE;
import static com.inq.wishhair.wesharewishhair.user.domain.point.PointType.USE;

@Getter
@AllArgsConstructor
public enum PointFixture {

    A(CHARGE, 100, 100),
    B(CHARGE, 1000, 1100),
    C(USE, 1000, 100),
    D(CHARGE, 2000, 2100),
    E(USE, 1000, 1100),
    ;
    private final PointType pointType;
    private final int dealAmount;
    private final int point;

    public PointHistory toEntity(User user) {
        return PointHistory.createPointHistory(user, pointType, dealAmount, point);
    }
}
