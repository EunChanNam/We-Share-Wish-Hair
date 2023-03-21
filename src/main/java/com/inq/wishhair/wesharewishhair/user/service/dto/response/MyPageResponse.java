package com.inq.wishhair.wesharewishhair.user.service.dto.response;

import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewResponse;
import com.inq.wishhair.wesharewishhair.user.domain.point.PointHistory;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MyPageResponse {

    private String nickname;

    private Sex sex;

    private Long point;

    private List<ReviewResponse> reviews;

    public MyPageResponse(User user, PointHistory pointHistory, List<ReviewResponse> reviewResponses) {
        this.nickname = user.getNickname();
        this.sex = user.getSex();
        this.point = pointHistory.getPoint();
        this.reviews = reviewResponses;
    }
}
