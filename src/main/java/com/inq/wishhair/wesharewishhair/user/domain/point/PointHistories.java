package com.inq.wishhair.wesharewishhair.user.domain.point;

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
}
