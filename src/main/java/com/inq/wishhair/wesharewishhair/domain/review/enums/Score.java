package com.inq.wishhair.wesharewishhair.domain.review.enums;

import lombok.Getter;

@Getter
public enum Score {
    S0(0.0),
    S0H(0.5),
    S1(1.0),
    S1H(1.5),
    S2(2.0),
    S2H(2.5),
    S3(3.0),
    S3H(3.5),
    S4(4.0),
    S4H(4.5),
    S5(5.0);

    Score(Double value) {
        this.value = value;
    }

    private final Double value;
}
