package com.inq.wishhair.wesharewishhair.user.service;

import com.inq.wishhair.wesharewishhair.user.controller.dto.request.PointUseRequest;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.domain.point.PointHistory;
import com.inq.wishhair.wesharewishhair.user.domain.point.PointRepository;
import com.inq.wishhair.wesharewishhair.user.domain.point.PointType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointService {

    private final PointRepository pointRepository;

    @Transactional
    public void insertPointHistory(PointType pointType, int dealAmount, User user) {

        user.updateAvailablePoint(pointType, dealAmount);
        PointHistory pointHistory = generatePointHistory(pointType, dealAmount, user);

        pointRepository.save(pointHistory);
    }

    private PointHistory generatePointHistory(PointType pointType, int dealAmount, User user) {
        int point;
        if (pointType.isCharge()) {
            point = dealAmount + user.getAvailablePoint();
        } else {
            point = user.getAvailablePoint() - dealAmount;
        }
        return PointHistory.createPointHistory(user, pointType, dealAmount, point);
    }
}

