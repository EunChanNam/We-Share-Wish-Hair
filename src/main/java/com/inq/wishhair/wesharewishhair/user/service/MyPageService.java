package com.inq.wishhair.wesharewishhair.user.service;

import com.inq.wishhair.wesharewishhair.review.service.ReviewSearchService;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewResponse;
import com.inq.wishhair.wesharewishhair.user.service.dto.response.MyPageResponse;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPageService {

    private final UserFindService userFindService;
    private final ReviewSearchService reviewSearchService;

    public MyPageResponse getMyPageInfo(Long userId, Pageable pageable) {

        List<ReviewResponse> reviewResponses = reviewSearchService.findLikingReviews(userId, pageable).getContent();
        User user = userFindService.findByUserId(userId);

        return new MyPageResponse(user, reviewResponses);
    }
}
