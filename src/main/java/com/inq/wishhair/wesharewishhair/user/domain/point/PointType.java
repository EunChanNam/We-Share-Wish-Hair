package com.inq.wishhair.wesharewishhair.user.domain.point;

public enum PointType {
    JOIN, //신규 가입
    CHARGE, //포인트 충전
    USE; // 포인트 사용

    public boolean isJoin() {
        return this == JOIN;
    }

    public boolean isCharge() {
        return this == CHARGE;
    }

    public boolean isUSE() {
        return this == USE;
    }
}
