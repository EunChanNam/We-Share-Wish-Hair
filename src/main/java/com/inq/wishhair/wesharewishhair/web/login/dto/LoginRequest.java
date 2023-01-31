package com.inq.wishhair.wesharewishhair.web.login.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequest {

    private final String loginId;

    private final String pw;
}
