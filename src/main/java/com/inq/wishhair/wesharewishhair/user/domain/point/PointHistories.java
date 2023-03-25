package com.inq.wishhair.wesharewishhair.user.domain.point;

import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.inq.wishhair.wesharewishhair.global.exception.ErrorCode.*;
import static com.inq.wishhair.wesharewishhair.user.domain.point.PointHistory.createPointHistory;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Embeddable
public class PointHistories {

    private int availablePoint = 0;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<PointHistory> pointHistories = new ArrayList<>();

    public void insertPointHistory(User user, PointType pointType, int dealAmount) {
        if (pointType.isCharge()) {
            validateChargeAmount(dealAmount);
            PointHistory chargePointHistory = createPointHistory(user, pointType, dealAmount, availablePoint + dealAmount);
            pointHistories.add(chargePointHistory);
            availablePoint += dealAmount;
        } else if (pointType.isUSE()) {
            validateUseAmount(dealAmount);
            PointHistory usePointHistory = createPointHistory(user, pointType, dealAmount, availablePoint - dealAmount);
            pointHistories.add(usePointHistory);
            availablePoint -= dealAmount;
        }
    }

    private void validateChargeAmount(int chargeAmount) {
        if (chargeAmount <= 0) {
            throw new WishHairException(POINT_INVALID_POINT_RANGE);
        }
    }

    private void validateUseAmount(int useAmount) {
        if (availablePoint - useAmount < 0) {
            throw new WishHairException(POINT_NOT_ENOUGH);
        }
    }
}
