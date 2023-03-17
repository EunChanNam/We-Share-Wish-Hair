package com.inq.wishhair.wesharewishhair.web.user.dto.response;

import com.inq.wishhair.wesharewishhair.domain.login.dto.UserSessionDto;
import com.inq.wishhair.wesharewishhair.domain.point.PointHistory;
import com.inq.wishhair.wesharewishhair.domain.user.User;
import com.inq.wishhair.wesharewishhair.domain.user.enums.Sex;
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
