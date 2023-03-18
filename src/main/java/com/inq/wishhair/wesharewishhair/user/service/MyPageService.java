package com.inq.wishhair.wesharewishhair.user.service;

import com.inq.wishhair.wesharewishhair.user.service.dto.response.MyPageResponse;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.domain.point.PointHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
