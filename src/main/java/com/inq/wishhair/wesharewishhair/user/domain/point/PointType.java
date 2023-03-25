package com.inq.wishhair.wesharewishhair.user.domain.point;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PointType {
    CHARGE("충전"), //포인트 충전
    USE("사용"); // 포인트 사용

    private final String description;

    public boolean isCharge() {
        return this == CHARGE;
    }

    public boolean isUSE() {
        return this == USE;
    }
}
