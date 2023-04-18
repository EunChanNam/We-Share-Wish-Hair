package com.inq.wishhair.wesharewishhair.user.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInformation {

    private String nickname;

    private boolean hasFaceShape;

    private String faceShapeTag;
}
