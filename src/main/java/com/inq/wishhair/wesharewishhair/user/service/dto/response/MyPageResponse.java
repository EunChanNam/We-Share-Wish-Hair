package com.inq.wishhair.wesharewishhair.user.service.dto.response;

import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewResponse;
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

    private int point;

    private List<ReviewResponse> reviews;

    public MyPageResponse(User user, List<ReviewResponse> reviewResponses) {
        this.nickname = user.getNicknameValue();
        this.sex = user.getSex();
        this.point = user.getAvailablePoint();
        this.reviews = reviewResponses;
    }
}
