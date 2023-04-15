package com.inq.wishhair.wesharewishhair.user.domain;

import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.user.domain.point.PointType;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.inq.wishhair.wesharewishhair.global.exception.ErrorCode.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class AvailablePoint {

    @Column(name = "available_point")
    private int value = 0;

    public void updateAvailablePoint(PointType pointType, int dealAmount) {
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
