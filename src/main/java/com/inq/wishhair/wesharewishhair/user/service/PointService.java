package com.inq.wishhair.wesharewishhair.user.service;

import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.domain.UserRepository;
import com.inq.wishhair.wesharewishhair.user.domain.point.PointHistory;
import com.inq.wishhair.wesharewishhair.user.domain.point.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.inq.wishhair.wesharewishhair.user.domain.point.PointType.CHARGE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointService {

    private final PointRepository pointRepository;
    private final UserRepository userRepository;

    @Transactional
    public void chargePoint(int dealAmount, Long userId) {

        User user = findUser(userId);

        user.updateAvailablePoint(CHARGE, dealAmount);
        PointHistory pointHistory = generateChargePointHistory(dealAmount, user);

        pointRepository.save(pointHistory);
    }

    private static PointHistory generateChargePointHistory(int dealAmount, User user) {
        return PointHistory.createPointHistory(user, CHARGE, dealAmount, user.getAvailablePoint() + dealAmount);
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new WishHairException(ErrorCode.NOT_EXIST_KEY));
    }
}

