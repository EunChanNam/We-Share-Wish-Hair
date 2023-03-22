package com.inq.wishhair.wesharewishhair.auth.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequest {

    private String email;

    private String pw;
}
