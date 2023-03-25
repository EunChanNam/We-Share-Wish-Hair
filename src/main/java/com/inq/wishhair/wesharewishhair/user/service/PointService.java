package com.inq.wishhair.wesharewishhair.user.service;

import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.domain.UserRepository;
import com.inq.wishhair.wesharewishhair.user.domain.point.PointHistory;
import com.inq.wishhair.wesharewishhair.user.domain.point.PointRepository;
import com.inq.wishhair.wesharewishhair.user.domain.point.PointType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.inq.wishhair.wesharewishhair.user.domain.point.PointType.CHARGE;
import static com.inq.wishhair.wesharewishhair.user.domain.point.PointType.USE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointService {

    private final PointRepository pointRepository;
    private final UserRepository userRepository;

    @Transactional
    public void insertPointHistory(PointType pointType, int dealAmount, Long userId) {
        User user = findUser(userId);

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

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new WishHairException(ErrorCode.NOT_EXIST_KEY));
    }
}

