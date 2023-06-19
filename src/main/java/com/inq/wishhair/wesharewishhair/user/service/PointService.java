package com.inq.wishhair.wesharewishhair.user.service;

import com.inq.wishhair.wesharewishhair.user.controller.dto.request.PointUseRequest;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.domain.point.PointType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointService {

    private final UserFindService userFindService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void usePoint(PointUseRequest request, Long userId) {

        User user = userFindService.findByUserId(userId);
        insertPointHistory(PointType.USE, request.getDealAmount(), user);

        eventPublisher.publishEvent(request.toRefundMailEvent(user.getName()));
    }

    @Transactional
    public void chargePoint(int dealAmount, Long userId) {
        User user = userFindService.findByUserId(userId);
        insertPointHistory(PointType.CHARGE, dealAmount, user);
    }

    private void insertPointHistory(PointType pointType, int dealAmount, User user) {
        user.updateAvailablePoint(pointType, dealAmount);
    }
}

