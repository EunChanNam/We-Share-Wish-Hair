package com.inq.wishhair.wesharewishhair.web.user.dto.response;

import com.inq.wishhair.wesharewishhair.domain.user.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyPageResponse {

    private String name;

    private String nickname;

    private Sex sex;

    private Integer point;
}
