package com.inq.wishhair.wesharewishhair.user.domain.point;

import com.inq.wishhair.wesharewishhair.user.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class PointHistories {

    private int availablePoint;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<PointHistory> pointHistories = new ArrayList<>();

    public void insertPointHistory(User user, PointType pointType, int dealAmount) {
        if (pointType.isCharge()) {
            PointHistory chargePointHistory =
                    PointHistory.createPointHistory(user, pointType, dealAmount, availablePoint + dealAmount);
            pointHistories.add(chargePointHistory);
        } else if (pointType.isUSE()) {
            PointHistory usePointHistory =
                    PointHistory.createPointHistory(user, pointType, dealAmount, availablePoint - dealAmount);
            pointHistories.add(usePointHistory);
        }
    }


}
