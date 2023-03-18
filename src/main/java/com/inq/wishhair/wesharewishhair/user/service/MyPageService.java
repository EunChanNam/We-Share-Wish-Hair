package com.inq.wishhair.wesharewishhair.user.service;

import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.user.controller.dto.response.MyPageResponse;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.domain.point.domain.PointHistory;
import com.inq.wishhair.wesharewishhair.user.domain.point.domain.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPageService {

    private final UserPointService userPointService;
    private final UserService userService;

    public MyPageResponse getMyPageInfo(Long userId) {

        PointHistory recentPoint = userPointService.getRecentPointHistory(userId);
        User user = userService.findByUserId(userId);

        return new MyPageResponse(user, recentPoint);
    }
}
