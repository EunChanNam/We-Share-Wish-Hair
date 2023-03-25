package com.inq.wishhair.wesharewishhair.user.domain;

import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.user.domain.point.PointType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.inq.wishhair.wesharewishhair.global.exception.ErrorCode.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Embeddable
public class AvailablePoint {

    @Column(name = "available_point")
    private int value = 0;

    public void updateAvailablePoint(User user, PointType pointType, int dealAmount) {
        if (pointType.isCharge()) {
            validateChargeAmount(dealAmount);
            value += dealAmount;
        } else if (pointType.isUSE()) {
            validateUseAmount(dealAmount);
            value -= dealAmount;
        }
    }

    private void validateChargeAmount(int chargeAmount) {
        if (chargeAmount <= 0) {
            throw new WishHairException(POINT_INVALID_POINT_RANGE);
        }
    }

    private void validateUseAmount(int useAmount) {
        if (value - useAmount < 0) {
            throw new WishHairException(POINT_NOT_ENOUGH);
        }
    }
}
