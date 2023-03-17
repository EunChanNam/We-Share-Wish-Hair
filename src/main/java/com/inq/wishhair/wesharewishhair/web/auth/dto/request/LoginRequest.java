package com.inq.wishhair.wesharewishhair.web.auth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequest {

    private String loginId;

    private String pw;
}
