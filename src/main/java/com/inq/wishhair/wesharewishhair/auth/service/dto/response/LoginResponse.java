package com.inq.wishhair.wesharewishhair.auth.service.dto.response;

import com.inq.wishhair.wesharewishhair.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {

    private UserInfo userInfo;
    private String accessToken;
    private String refreshToken;

    public LoginResponse(User user, String accessToken, String refreshToken) {
        this.userInfo = new UserInfo(user);
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
