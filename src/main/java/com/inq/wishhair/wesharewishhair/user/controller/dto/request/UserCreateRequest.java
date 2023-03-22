package com.inq.wishhair.wesharewishhair.user.controller.dto.request;

import com.inq.wishhair.wesharewishhair.user.domain.Email;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserCreateRequest {

    private String email;

    private String pw;

    private String name;

    private String nickname;

    private Sex sex;

    //==생성 메서드를 통해 엔티티로 전환==//
    public User toEntity() {
        return User.builder()
                .email(new Email(email))
                .pw(pw)
                .name(name)
                .nickname(nickname)
                .sex(sex)
                .build();
    }
}
