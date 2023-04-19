package com.inq.wishhair.wesharewishhair.user.service.dto.response;

import com.inq.wishhair.wesharewishhair.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInformation {

    private String nickname;

    private boolean hasFaceShape;

    private String faceShapeTag;

    public UserInformation(User user) {
        this.nickname = user.getNicknameValue();
        this.hasFaceShape = user.existFaceShape();
        this.faceShapeTag = user.getFaceShape().getDescription();
    }
}
