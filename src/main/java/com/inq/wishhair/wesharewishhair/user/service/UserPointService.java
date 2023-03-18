package com.inq.wishhair.wesharewishhair.user.service;

import com.inq.wishhair.wesharewishhair.user.domain.point.domain.PointHistory;
import com.inq.wishhair.wesharewishhair.user.domain.point.domain.PointRepository;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserPointService {

    private final PointRepository pointRepository;

    public PointHistory getRecentPointHistory(Long userId) {

        Pageable pageable = PageRequest.of(0, 1); // limit 1 의 역할

        // Optional 로 처리하려면 User 에 대한 쿼리가 추가적으로 필요해서 List 로 처리
        List<PointHistory> pointHistories = pointRepository.findRecentPointByUserId(userId, pageable);
        if (pointHistories.isEmpty()) throw new WishHairException(ErrorCode.NOT_EXIST_KEY);
        return pointHistories.get(0);
    }
}
