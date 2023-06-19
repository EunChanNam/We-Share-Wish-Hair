package com.inq.wishhair.wesharewishhair.user.domain.point;

import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;

import javax.persistence.*;

import com.inq.wishhair.wesharewishhair.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.inq.wishhair.wesharewishhair.global.exception.ErrorCode.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Embeddable
public class Points {

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private final List<PointHistory> pointHistories = new ArrayList<>();

    private int availablePoint = 0;

    public void updateAvailablePoint(PointType pointType, int dealAmount, User user) {
        if (pointType.isCharge()) {
            validateChargeAmount(dealAmount);
            availablePoint += dealAmount;
        } else if (pointType.isUSE()) {
            validateUseAmount(dealAmount);
            availablePoint -= dealAmount;
        }

        addPointHistory(pointType, dealAmount, availablePoint, user);
    }

    private void validateChargeAmount(int chargeAmount) {
        if (chargeAmount <= 0) {
            throw new WishHairException(POINT_INVALID_POINT_RANGE);
        }
    }

    private void validateUseAmount(int useAmount) {
        if (useAmount <= 0 || availablePoint - useAmount < 0) {
            throw new WishHairException(POINT_NOT_ENOUGH);
        }
    }

    private void addPointHistory(PointType pointType, int dealAmount, int point, User user) {
        pointHistories.add(PointHistory.createPointHistory(user, pointType, dealAmount, point));
    }
}
