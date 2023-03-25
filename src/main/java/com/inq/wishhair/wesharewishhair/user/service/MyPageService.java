package com.inq.wishhair.wesharewishhair.user.service;

import com.inq.wishhair.wesharewishhair.review.service.ReviewFindService;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewResponse;
import com.inq.wishhair.wesharewishhair.user.service.dto.response.MyPageResponse;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.domain.point.PointHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPageService {

    private final PointFindService userPointService;
    private final UserFindService userFindService;
    private final ReviewFindService reviewFindService;

    public MyPageResponse getMyPageInfo(Long userId, Pageable pageable) {

        List<ReviewResponse> reviewResponses = reviewFindService.findLikingReviews(userId, pageable);
        User user = userFindService.findByUserId(userId);

        return new MyPageResponse(user, reviewResponses);
    }
}
