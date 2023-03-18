package com.inq.wishhair.wesharewishhair.user.controller.dto.response;

import com.inq.wishhair.wesharewishhair.user.domain.point.PointHistory;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyPageResponse {

    private String name;

    private String nickname;

    private Sex sex;

    private Long point;

    public MyPageResponse(User user, PointHistory pointHistory) {
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.sex = user.getSex();
        this.point = pointHistory.getPoint();
    }
}
