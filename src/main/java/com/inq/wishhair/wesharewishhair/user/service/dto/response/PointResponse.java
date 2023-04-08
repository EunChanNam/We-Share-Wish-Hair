package com.inq.wishhair.wesharewishhair.user.service.dto.response;

import com.inq.wishhair.wesharewishhair.user.domain.point.PointHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PointResponse {

    private String pointType;

    private int dealAmount;

    private int point;

    private LocalDateTime dealDate;

    public PointResponse(PointHistory pointHistory) {
        this.pointType = pointHistory.getPointType().getDescription();
        this.dealAmount = pointHistory.getDealAmount();
        this.point = pointHistory.getPoint();
        this.dealDate = pointHistory.getCreatedDate();
    }
}
