package com.inq.wishhair.wesharewishhair.auth.service.dto.response;

import com.inq.wishhair.wesharewishhair.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {

    private UserInformation userInfo;
    private String accessToken;
    private String refreshToken;

    public LoginResponse(User user, String accessToken, String refreshToken) {
        this.userInfo = new UserInformation(user);
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
