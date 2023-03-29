package com.inq.wishhair.wesharewishhair.user.controller.dto.request;

import com.inq.wishhair.wesharewishhair.user.domain.Email;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.enums.Sex;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserCreateRequest {

    @NotNull
    private String email;

    @NotNull
    private String pw;

    @NotNull
    private String name;

    @NotNull
    private String nickname;

    @NotNull
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
