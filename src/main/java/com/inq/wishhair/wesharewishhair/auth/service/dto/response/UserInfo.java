package com.inq.wishhair.wesharewishhair.auth.service.dto.response;

import com.inq.wishhair.wesharewishhair.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfo {

    private String nickname;

    private boolean hasFaceShape;

    private String faceShapeTag;

    public UserInfo(User user) {
        this.nickname = user.getNicknameValue();
        this.hasFaceShape = user.existFaceShape();
        this.faceShapeTag = user.getFaceShapeTag().getDescription();
    }
}
