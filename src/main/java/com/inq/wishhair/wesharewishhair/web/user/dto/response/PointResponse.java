package com.inq.wishhair.wesharewishhair.web.user.dto.response;

import com.inq.wishhair.wesharewishhair.domain.point.PointHistory;
import com.inq.wishhair.wesharewishhair.domain.point.enums.PointType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PointResponse {

    private PointType pointType;

    private Long dealAmount;

    private Long point;

    private LocalDateTime createdDate;

    public PointResponse(PointHistory pointHistory) {
        this.pointType = pointHistory.getPointType();
        this.dealAmount = pointHistory.getDealAmount();
        this.point = pointHistory.getPoint();
        this.createdDate = pointHistory.getCreatedDate();
    }
}
