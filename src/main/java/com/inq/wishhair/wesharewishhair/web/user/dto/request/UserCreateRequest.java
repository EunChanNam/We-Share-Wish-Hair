package com.inq.wishhair.wesharewishhair.web.user.dto.request;

import com.inq.wishhair.wesharewishhair.domain.user.User;
import com.inq.wishhair.wesharewishhair.domain.user.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserCreateRequest {

    private String loginId;

    private String pw;

    private String name;

    private String nickname;

    private Sex sex;

    //==생성 메서드를 통해 엔티티로 전환==//
    public User toEntity() {
        return User.createUser(
                this.loginId,
                this.pw,
                this.name,
                this.nickname,
                this.sex
        );
    }
}
