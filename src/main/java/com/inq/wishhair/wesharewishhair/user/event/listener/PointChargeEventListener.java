package com.inq.wishhair.wesharewishhair.user.event.listener;

import com.inq.wishhair.wesharewishhair.review.event.PointChargeEvent;
import com.inq.wishhair.wesharewishhair.user.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class PointChargeEventListener {

    private final PointService pointService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void chargePoint(PointChargeEvent event) {
        pointService.chargePoint(event.dealAmount(), event.userId());
    }
}
