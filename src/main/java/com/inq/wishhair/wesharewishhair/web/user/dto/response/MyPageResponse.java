package com.inq.wishhair.wesharewishhair.web.user.dto.response;

import com.inq.wishhair.wesharewishhair.domain.login.dto.UserSessionDto;
import com.inq.wishhair.wesharewishhair.domain.point.PointHistory;
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

    public MyPageResponse(UserSessionDto userSessionDto, PointHistory pointHistory) {
        this.name = userSessionDto.getName();
        this.nickname = userSessionDto.getNickname();
        this.sex = userSessionDto.getSex();
        this.point = pointHistory.getPoint();
    }
}
