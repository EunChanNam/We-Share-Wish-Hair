package com.inq.wishhair.wesharewishhair.web.login.dto;

import lombok.Getter;

@Getter
public class LoginRequest {

    private final String loginId;

    private final String pw;

    public LoginRequest(String loginId, String pw) {
        this.loginId = loginId;
        this.pw = pw;
    }
}
